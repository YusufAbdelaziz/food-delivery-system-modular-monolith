package com.joe.abdelaziz.foodDeliverySystem.user.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.AppUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The Admin class represents an admin user. It extends the AppUser class and adds additional
 * functionality specific to an admin user.
 */
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends AppUser {}









