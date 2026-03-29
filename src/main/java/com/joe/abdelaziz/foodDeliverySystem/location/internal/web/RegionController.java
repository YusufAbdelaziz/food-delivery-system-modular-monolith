package com.joe.abdelaziz.foodDeliverySystem.location.internal.web;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.RegionDto;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
@Tag(name = "Region", description = "API for managing regions")
public class RegionController {

  private final RegionService regionService;

  @Operation(summary = "Add a new region", description = "Creates a new region")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Region created successfully",
            content = @Content(schema = @Schema(implementation = RegionDto.class))),
        @ApiResponse(responseCode = "403", description = "User is not authorized"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
      })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<RegionDto> addRegion(
      @RequestBody @Parameter(description = "Region data", required = true) RegionDto region) {
    return ResponseEntity.ok().body(regionService.insertRegion(region));
  }

  @Operation(summary = "Get region by ID", description = "Fetches a region by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Region retrieved successfully",
            content = @Content(schema = @Schema(implementation = RegionDto.class))),
        @ApiResponse(responseCode = "403", description = "User is not authorized"),
        @ApiResponse(responseCode = "404", description = "Region not found")
      })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/{id}")
  public ResponseEntity<RegionDto> getRegionById(
      @PathVariable @Parameter(description = "Region ID", required = true) Long id) {
    return ResponseEntity.ok().body(regionService.findRegionDtoById(id));
  }

  @Operation(summary = "Get all regions", description = "Fetches all regions")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Regions retrieved successfully",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = RegionDto.class)))),
        @ApiResponse(responseCode = "403", description = "User is not authorized")
      })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("")
  public ResponseEntity<List<RegionDto>> getAllRegions() {
    return ResponseEntity.ok().body(regionService.findAll());
  }
}







