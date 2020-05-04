package server.data.DAOs;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import server.data.DTOs.UserShortTO;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Users")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDAO {
    @Id
    private ObjectId _id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;

    @Builder.Default
    private boolean isEmailValidated = false;
    @Builder.Default
    private List<String> favourites = new ArrayList<>();
    private String IPAddress;

    public UserShortTO mapToFav() {
        return new UserShortTO(_id.toString(), username, true);
    }

    public UserShortTO mapToNotFav() {
        return new UserShortTO(_id.toString(), username, false);
    }
}
