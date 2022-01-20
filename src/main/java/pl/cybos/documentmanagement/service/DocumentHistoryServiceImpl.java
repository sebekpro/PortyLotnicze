package pl.cybos.documentmanagement.service;

import org.springframework.data.domain.Pageable;
import pl.cybos.documentmanagement.model.DocumentHistory;

import java.util.List;

public class DocumentHistoryServiceImpl implements DocumentHistoryService{

    @Override
    public DocumentHistory createDocument(DocumentHistory document) {
        return null;
    }

    @Override
    public DocumentHistory updateDocument(DocumentHistory document) {
        return null;
    }

    @Override
    public List<DocumentHistory> readAllPageableDocuments(Pageable pageable) {
        return null;
    }

    @Override
    public List<DocumentHistory> readAllDocuments() {
        return null;
    }

    @Override
    public DocumentHistory readDocument(Integer id) {
        return null;
    }

    @Override
    public DocumentHistory deleteDocument(Integer id) {
        return null;
    }
}
