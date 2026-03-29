package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.SpecType;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.SpecOption;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Spec extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "spec_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private SpecType type;

  @NotBlank(message = "Spec name should not be blank")
  private String name;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "spec_id")
  @JsonIgnoreProperties("spec")
  private Set<SpecOption> options = new HashSet<>();

  public void addOption(SpecOption option) {
    options.add(option);
  }

  public void addOptions(List<SpecOption> newOptions) {
    options.addAll(newOptions);
  }
}







