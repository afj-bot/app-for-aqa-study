package com.afj.solution.buyitapp.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.afj.solution.buyitapp.model.enums.OrderStatus;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_order")
@ToString
public class Order implements Serializable {

    private static final long serialVersionUID = -1772111284653410502L;

    @Id
    @Type(type = "uuid-binary")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Type(type = "uuid-binary")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UUID> productIds = new HashSet<>();

    @Column(name = "total")
    private float total;

    @Column(name = "status", columnDefinition = "ENUM('PENDING', 'WAITING_FOR_PAYMENT', 'IN_PROGRESS', 'SHIPPED', 'DONE')")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    public Order(final Consumer<Order> builder) {
        requireNonNull(builder).accept(this);
    }

}
