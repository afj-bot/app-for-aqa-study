package com.afj.solution.buyitapp.model.category;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import static java.util.Objects.requireNonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Tomash Gombosh
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category_localization")
public class CategoryLocalization {

    @Id
    @Type(type = "uuid-binary")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "locale")
    private String locale;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonBackReference
    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    public CategoryLocalization(final Consumer<CategoryLocalization> builder) {
        requireNonNull(builder).accept(this);
    }


    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\", \"name\": \"%s\", \"description\": \"%s\", "
                        + "\"locale\": \"%s\", \"created_at\": \"%s\", \"updated_at\": \"%s\" }",
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getLocale(),
                this.getCreatedAt(),
                this.getUpdatedAt());
    }
}
