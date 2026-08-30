package org.example.security;

import org.example.model.Role;
import org.example.model.User;
import org.example.model.item.ItemDTO;
import org.example.repository.UserRepo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class SecurityUtil {

    public static boolean checkUser(AuthenticatedUser user, UUID committeeId){
        return user.role() != Role.LIS || committeeId.equals(user.committeeId());
    }

    public static boolean checkUser(User user, UUID committeeId){
        return user.getRole() != Role.LIS || committeeId.equals(user.getCommittee_id());
    }

    public static boolean checkAuthor(UserRepo userRepo, ItemDTO request, AuthenticatedUser currentUser){
        UUID authorId = UUID.fromString(request.authorId());
        UUID committeeId = UUID.fromString(request.committeeId());

        return userRepo.findById(authorId)
                .map(author -> {
                    // System.out.println("Both ids: "+committeeId+" - "+author.getCommittee_id());
                    if (Objects.equals(author.getId(), currentUser.id())){
                        return checkUser(currentUser, committeeId);
                    }
                    return SecurityVariables.authorizedRoles.contains(currentUser.role().toString()) && checkUser(author, committeeId);
                })
                .orElse(false);
    }

    public static boolean checkAuthors(UserRepo userRepo, List<? extends ItemDTO> requests, AuthenticatedUser currentUser){
        for(ItemDTO request : requests){
            if (!checkAuthor(userRepo, request, currentUser)){
                return false;
            }
        }
        return true;
    }
}
