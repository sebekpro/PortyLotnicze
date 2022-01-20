package pl.cybos.documentmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cybos.documentmanagement.model.Document;
import pl.cybos.documentmanagement.service.DocumentServiceImpl;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentServiceImpl documentService;


    @GetMapping(value="/documents")
    ResponseEntity<List<Document>> readAllDocuments(@PageableDefault(size=10, sort = {"name", "status"}) Pageable page){
        log.info("Pobrałem wszystkie wnioski z paginacją");
        return ResponseEntity.ok(documentService.readAllPageableDocuments(page));
    }

    @PutMapping(value="/documents/{id}")
    ResponseEntity<?> updateDocument(@PathVariable int id, @RequestBody @Valid Document document){

        if(!documentService.updateDocument(document, id)) {
            log.info("Nie znaleziono wniosku o id = " + id);
            return ResponseEntity.notFound().build();
        }
        log.info("Aktualizacja wniosku nr " + id);
        return ResponseEntity.created((URI.create("/documents/" + document.getId()))).build();
    }

    @PostMapping(value="/documents")
    ResponseEntity<?> saveDocument(@RequestBody @Valid Document document){
        Document documentCreate = documentService.createDocument(document);

        if (documentCreate == null){
            return ResponseEntity.badRequest().build();
        }

        log.info("Zapisanie wniosku nr " + document.getId());

        return ResponseEntity.created((URI.create("/documents/" + document.getId()))).build();
    }

    @DeleteMapping(value="/documents/{id}")
    ResponseEntity<?> deleteDocument(@PathVariable int id){

        if(!documentService.deleteDocument(id)) {
             return ResponseEntity.badRequest().build();
        }
        log.info("Usunięto wniosek nr: " + id);
        return ResponseEntity.noContent().build();
    }

}
