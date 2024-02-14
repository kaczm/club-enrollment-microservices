package org.bp.project;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ClubIdentifierService {
	public String getClubIdentifier() {
		return UUID.randomUUID().toString();
	}
}
