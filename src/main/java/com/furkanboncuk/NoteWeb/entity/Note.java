package com.furkanboncuk.NoteWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notes_id")
    private long id;
    @Column(name="notes_title",nullable = false)
    @NotBlank(message="The 'title' field must not be left blank")
    @Size(min = 1, max = 75, message = "The 'title' field is too long")
    private String title;
    @Column(name="notes_category",nullable = false)
    @NotBlank(message="The 'category' field must not be left blank")
    @Size(min = 1, max = 75, message = "The 'category' field is too long")
    private String category;
    @CreationTimestamp
    @Column(name="notes_created_date",nullable = false)
    private Date createdDate;
    @UpdateTimestamp
    @Column(name="notes_updated_date", nullable = false)
    private Date updatedDate;
    @Column(name="note_content", nullable = false)
    @NotBlank(message="The 'content' field must not be left blank")
    private String content;
}
