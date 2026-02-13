package org;


private void mapValueToDto(ParticipantRowDto dto, String columnName, String value) {

    switch (columnName) {
        case "seeker name":
            dto.setSeekerName(value);
            break;

        case "seeker email":
            dto.setSeekerEmail(value);
            break;

        case "seeker phone no.":
            dto.setSeekerPhone(value);
            break;

        case "provider name":
            dto.setProviderName(value);
            break;

        case "provider email":
            dto.setProviderEmail(value);
            break;

        case "relationship with seeker":
            dto.setRelationshipWithSeeker(value);
            break;

        case "is family related":
            dto.setFamilyRelated(Boolean.parseBoolean(value.toLowerCase()));
            break;
    }
}