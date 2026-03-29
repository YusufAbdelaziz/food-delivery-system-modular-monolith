package com.joe.abdelaziz.foodDeliverySystem.user.admin;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.user.api.dto.AdminDTO;
import com.joe.abdelaziz.foodDeliverySystem.user.api.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Admin", description = "API for managing admins")
public class AdminController {

  private final AdminService adminService;
  private final RestaurantService restaurantService;

  @Operation(summary = "Create a new admin", description = "Creates a new admin user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Admin created successfully",
            content = @Content(schema = @Schema(implementation = AdminDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
      })
  @PostMapping("")
  public ResponseEntity<AdminDTO> createAdmin(
      @Valid @RequestBody @Parameter(description = "Admin user details", required = true)
          AdminDTO user) {
    log.info("Request received: " + user);
    return ResponseEntity.ok().body(adminService.insertAdmin(user));
  }

  @Operation(summary = "Get admin by ID", description = "Fetches an admin by their ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Admin retrieved successfully",
            content = @Content(schema = @Schema(implementation = AdminDTO.class))),
        @ApiResponse(responseCode = "404", description = "Admin not found")
      })
  @GetMapping("/{id}")
  public ResponseEntity<AdminDTO> getAdminById(
      @PathVariable @Parameter(description = "Admin ID", required = true) Long id) {
    return ResponseEntity.ok().body(adminService.findAdminById(id));
  }

  @Operation(summary = "Get all admins", description = "Fetches all admin users")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Admins retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AdminDTO.class))))
      })
  @GetMapping("")
  public ResponseEntity<List<AdminDTO>> getAllAdmins() {
    return ResponseEntity.ok().body(adminService.findAll());
  }

  @Operation(summary = "Delete restaurant address", description = "Deletes an address owned by the specified restaurant")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Address or restaurant not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
      })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/restaurants/{restaurantId}/addresses/{addressId}")
  public ResponseEntity<Void> deleteRestaurantAddress(
      @PathVariable @Parameter(description = "Restaurant ID", required = true) Long restaurantId,
      @PathVariable @Parameter(description = "Address ID", required = true) Long addressId) {
    restaurantService.removeRestaurantAddress(restaurantId, addressId);
    return ResponseEntity.noContent().build();
  }
}






