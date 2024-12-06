package com.booking.receptionists.mapper;

import com.booking.receptionists.dtos.request.ReceptionistRequest;
import com.booking.receptionists.dtos.response.ReceptionistResponse;
import com.booking.receptionists.entity.ReceptionistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceptionistMapper {
    ReceptionistEntity toReceptionist(ReceptionistRequest request);
    @Mapping(target = "user.role", ignore = true)
    ReceptionistResponse toReceptionistResponse(ReceptionistEntity entity);
    void updateReceptionist(@MappingTarget ReceptionistEntity entity, ReceptionistRequest request);
}
