package tech.skullprogrammer.core.utility;

import org.bson.types.ObjectId;
import tech.skullprogrammer.core.exception.GenericErrorPayload;
import tech.skullprogrammer.core.exception.SkullErrorDefaultImpl;
import tech.skullprogrammer.core.exception.SkullResourceException;

public class Utility {

    public static ObjectId verifyObjectId(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw SkullResourceException.builder()
                    .error(SkullErrorDefaultImpl.BAD_REQUEST)
                    .payload(GenericErrorPayload.builder()
                            .putInfo("id", ex.getMessage())
                            .build())
                    .build();
        }
    }

}
