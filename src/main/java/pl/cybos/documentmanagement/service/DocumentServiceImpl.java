package pl.cybos.documentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cybos.documentmanagement.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentRepository documentRepo;

    @Autowired
    private DocumentHistoryRepository documentHistoryRepo;

    @Override
    @Transactional
    public Document createDocument(Document document) {

        if(document.getStatus().equals(Status.CREATED)) {
            documentRepo.save(document);
            DocumentHistory documentHistory = documentToDocumentHistory(document);
            documentHistoryRepo.save(documentHistory);

        } else {
            return null;
        }
        return this.documentRepo.save(document);
    }

    @Override
    @Transactional
    public boolean updateDocument(Document document, Integer id) {

        Document documentFromDb = null;

        if (documentRepo.existsById(id)) {
            documentFromDb = documentRepo.findById(id).get();
        } else {
            return false;
        }

        // The content of the document can only change with the states CREATED and VERIFIED
        DocumentHistory documentHistory = null;
        switch (document.getStatus()) {

            case CREATED:
                case VERIFIED: {
                    documentFromDb.setDescription(document.getDescription());
                    documentFromDb.setStatus(document.getStatus());

                    documentRepo.save(documentFromDb);

                    documentHistory = documentToDocumentHistory(documentFromDb);

                    // Keeping a complete history of application status changes
                    documentHistoryRepo.save(documentHistory);

                    break;
                }
            case REJECTED:
                // With the status REJECTED, change the status and add a reason
                documentFromDb.setStatus(document.getStatus());
                documentFromDb.setReason(document.getReason());
                documentRepo.save(documentFromDb);

                documentHistory = documentToDocumentHistory(documentFromDb);

                // Keeping a complete history of application status changes
                documentHistoryRepo.save(documentHistory);

                break;

            case ACCEPTED:
                case PUBLISHED: {
                    // For ACCEPTED and PUBLISHED statuses, we only change the status

                    documentFromDb.setStatus(document.getStatus());
                    documentRepo.save(documentFromDb);

                    documentHistory = documentToDocumentHistory(documentFromDb);

                    // Keeping a complete history of application status changes
                    documentHistoryRepo.save(documentHistory);

                    break;
                }
            default:
                return false;

        }


        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Document> readAllPageableDocuments( Pageable pageable) {
        return documentRepo.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> readAllDocuments() {
        return documentRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Document readDocument(Integer id) {
        return documentRepo.findById(id).get();
    }

    @Override
    @Transactional
    public boolean deleteDocument(Integer id) {
        Document document = null;

        if (documentRepo.existsById(id)) {
            document = documentRepo.findById(id).get();
        } else {
            return false;
        }

        if (document.getStatus().equals(Status.CREATED)) {

            DocumentHistory documentHistory = documentToDocumentHistory(document);

            documentRepo.delete(document);

            // Keeping a complete history of application status changes
            documentHistory.setStatus(Status.DELETED);
            documentHistoryRepo.save(documentHistory);
            return true;
        }

        return false;
    }


    private DocumentHistory documentToDocumentHistory(Document document){

        DocumentHistory documentHistory = new DocumentHistory();

        documentHistory.setIdDocument(document.getId());
        documentHistory.setName(document.getName());
        documentHistory.setDescription(document.getDescription());
        documentHistory.setStatus(document.getStatus());
        documentHistory.setReason(document.getReason());
        documentHistory.setDateTime(new Date());

        return documentHistory;

    }


}
