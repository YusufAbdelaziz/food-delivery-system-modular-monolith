package com.joe.abdelaziz.foodDeliverySystem.location.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Address;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.lang.NonNull;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AddressMapper {

  @NonNull
  @Mapping(target = "regionId", expression = "java(address.getRegion() != null ? address.getRegion().getId() : null)")
  public abstract AddressDTO toAddressDto(Address address);

  @Mappings(value = {
      @Mapping(target = "region", expression = "java(mapRegion(addressDto.getRegionId()))"),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract Address toAddress(AddressDTO addressDto);

  @Mappings(value = {
      @Mapping(target = "region", expression = "java(mapRegion(dto.getRegionId()))"),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract Address toUpdatedAddress(AddressDTO dto, @MappingTarget Address existingAddress);

  protected Region mapRegion(Long regionId) {
    if (regionId == null) {
      return null;
    }
    Region region = new Region();
    region.setId(regionId);
    return region;
  }
}
