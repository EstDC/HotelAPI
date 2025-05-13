package com.hotel.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ReservaExtraId implements Serializable {
    private Long reservaId;
    private Long extraId;
} 