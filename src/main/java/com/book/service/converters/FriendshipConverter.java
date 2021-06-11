package com.book.service.converters;

import org.springframework.stereotype.Service;

import com.book.model.dto.FriendshipDTO;
import com.book.model.entity.relationship.Friendship;
import com.book.service.abstracts.DtoConverter;

/* Implements DtoConverter for friendship 
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class FriendshipConverter extends DtoConverter<Friendship, FriendshipDTO> {
	
	@Override
	public Friendship fromDto(FriendshipDTO dto) {
		return null;
	}

	@Override
	public FriendshipDTO fromEntity(Friendship entity) {
		FriendshipDTO dto = new FriendshipDTO();
		dto.setId(entity.getId());
		dto.setFriendId(entity.getFriend().getUser().getId());
		dto.setFriendName(entity.getFriend().getUser().getUsername());
		dto.setRequesterId(entity.getRequester().getUser().getId());
		dto.setRequesterName(entity.getRequester().getUser().getUsername());
		dto.setAccepted(entity.getAccepted());
		
		return dto;
	}

	
}
