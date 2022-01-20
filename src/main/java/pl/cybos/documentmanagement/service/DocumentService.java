package pl.cybos.documentmanagement.service;

import org.springframework.data.domain.Pageable;
import pl.cybos.documentmanagement.model.Document;

import java.util.List;

public interface DocumentService {

    Document createDocument(Document document);
    boolean updateDocument(Document document, Integer id);
    List<Document> readAllPageableDocuments(Pageable pageable);
    List<Document> readAllDocuments();
    Document readDocument(Integer id);
    boolean deleteDocument(Integer id);

}
