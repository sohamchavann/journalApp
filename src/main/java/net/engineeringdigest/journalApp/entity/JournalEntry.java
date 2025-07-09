package net.engineeringdigest.journalApp.entity;

import lombok.Data;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data //Lombok Integration: Reduces Boiler Plate Code like getter setter or funcs like .equals etc
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;
}
