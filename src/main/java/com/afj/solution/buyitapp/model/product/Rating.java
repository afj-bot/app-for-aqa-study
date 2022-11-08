package com.afj.solution.buyitapp.model.product;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.afj.solution.buyitapp.model.Order;

import static java.lang.String.format;
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
@Table(name = "rating")
public class Rating implements Serializable {

    private static final long serialVersionUID = 4630427148855205084L;

    @Id
    @Type(type = "uuid-binary")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "star")
    private int star;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @JsonManagedReference
    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public String toString() {
        return format("{ \"id\": \"%s\", \"product_id\": \"%s\", "
                        + "\"comment\": \"%s\",}",
                this.getId(),
                this.getProduct().getId(),
                this.getComment());
    }

    public Rating(final Consumer<Rating> builder) {
        requireNonNull(builder).accept(this);
    }
}
