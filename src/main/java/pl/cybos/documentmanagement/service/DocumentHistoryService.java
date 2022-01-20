package pl.cybos.documentmanagement.service;

import org.springframework.data.domain.Pageable;
import pl.cybos.documentmanagement.model.Document;
import pl.cybos.documentmanagement.model.DocumentHistory;

import java.util.List;

public interface DocumentHistoryService {

    DocumentHistory createDocument(DocumentHistory document);
    DocumentHistory updateDocument(DocumentHistory document);
    List<DocumentHistory> readAllPageableDocuments(Pageable pageable);
    List<DocumentHistory> readAllDocuments();
    DocumentHistory readDocument(Integer id);
    DocumentHistory deleteDocument(Integer id);

}
