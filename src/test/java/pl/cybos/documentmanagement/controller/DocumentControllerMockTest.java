package pl.cybos.documentmanagement.controller;

import org.assertj.core.api.AbstractBigIntegerAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.assertThat;
import pl.cybos.documentmanagement.model.Document;
import pl.cybos.documentmanagement.model.Status;
import pl.cybos.documentmanagement.service.DocumentService;
import pl.cybos.documentmanagement.service.DocumentServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DocumentControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private DocumentServiceImpl documentServiceImpl;

    @InjectMocks
    private DocumentController documentController;

    @Test
    void testreadAllDocuments() {

        Document document1 = new Document();
        Document document2 = new Document();
        RestTemplate restTemplate;


        String url = "/documents";

        document1.setId(1);
        document1.setName("Document1");
        document1.setDescription("Opis 1");
        document1.setStatus(Status.CREATED);
        document1.setDateTime(new Date());

        document2.setId(2);
        document2.setName("Document2");
        document2.setDescription("Opis 2");
        document2.setStatus(Status.VERIFIED);
        document2.setDateTime(new Date());

        List<Document> documents = new ArrayList<Document>();
        documents.add(document1);
        documents.add(document2);
        Pageable page = PageRequest.of(0, 10, Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.desc("status")));


        when(documentServiceImpl.readAllPageableDocuments(page)).thenReturn(documents);

        // when
        ResponseEntity<List<Document>> result = documentController.readAllDocuments(page);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);

        assertThat(result.getBody().size()).isEqualTo(2);

        assertThat(result.getBody().get(0).getName())
                .isEqualTo(document1.getName());

        assertThat(result.getBody().get(1).getName())
                .isEqualTo(document2.getName());
    }

    @Test
    void testCreateDocument() {


        Document document1 = new Document();

        String url = "/documents";

        document1.setId(1);
        document1.setName("Document1");
        document1.setDescription("Opis 1");
        document1.setStatus(Status.CREATED);
        document1.setDateTime(new Date());

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(documentServiceImpl.createDocument(any(Document.class))).thenReturn(new Document());

        
        ResponseEntity<?> responseEntity = documentController.saveDocument(document1);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo(url + "/" + document1.getId());
    }



    @Test
    void testUpdateDocument() {


        Document document1 = new Document();

        String url = "/documents";
        Integer id = 1;

        document1.setId(id);
        document1.setName("Document1");
        document1.setDescription("Opis 1");
        document1.setStatus(Status.CREATED);
        document1.setDateTime(new Date());

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(documentServiceImpl.updateDocument(document1, id)).thenReturn(true);

        ResponseEntity<?> responseEntity = documentController.updateDocument(id, document1);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo(url + "/" + document1.getId());
    }

    @Test
    void testDeleteDocument() {


        Document document1 = new Document();

        String url = "/documents";
        Integer id = 1;

        document1.setId(id);
        document1.setName("Document1");
        document1.setDescription("Opis 1");
        document1.setStatus(Status.CREATED);
        document1.setDateTime(new Date());

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(documentServiceImpl.deleteDocument(id)).thenReturn(true);

        ResponseEntity<?> responseEntity = documentController.deleteDocument(id);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

    }


}
