package com.afj.solution.buyitapp.model.category;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.afj.solution.buyitapp.model.Product;

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
@Table(name = "category")
public class Category {

    @Id
    @Type(type = "uuid-binary")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = EAGER, cascade = ALL)
    private Set<CategoryLocalization> localizations = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = EAGER, cascade = ALL)
    private Set<SubCategory> subCategories = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "category", fetch = EAGER, cascade = ALL)
    private Product product;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\", \"name\": \"%s\", \"description\": \"%s\", "
                        + "\"localization\": \"%s\", \"subCategories\": \"%s\", "
                        + " \"created_at\": \"%s\", \"updated_at\": \"%s\" }",
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getLocalizations(),
                this.getSubCategories(),
                this.getCreatedAt(),
                this.getUpdatedAt());
    }

}
