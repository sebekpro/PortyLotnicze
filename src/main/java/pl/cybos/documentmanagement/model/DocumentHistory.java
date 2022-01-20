package pl.cybos.documentmanagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class DocumentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idDocument;
    private String name;
    private String description;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    public DocumentHistory(){}

}
