package com.booking.receptionists.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.booking.receptionists.dtos.request.ReceptionistRequest;
import com.booking.receptionists.dtos.response.ReceptionistResponse;
import com.booking.receptionists.entity.ReceptionistEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReceptionistMapper {
    ReceptionistEntity toReceptionist(ReceptionistRequest request);

    ReceptionistResponse toReceptionistResponse(ReceptionistEntity entity);

    void updateReceptionist(@MappingTarget ReceptionistEntity entity, ReceptionistRequest request);
}
