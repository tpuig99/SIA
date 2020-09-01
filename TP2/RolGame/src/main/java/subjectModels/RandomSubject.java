package subjectModels;

import subjectModels.Constants.Role;
import subjectModels.equipment.Item;
import subjectModels.roles.Character;

public interface RandomSubject {
    Item randomProperty(int property);
    Character randomCharacter(Role role);
}
