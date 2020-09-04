
import crossovers.*;
import finishCriteria.*;
import implementations.FillAllImplementation;
import implementations.FillParentImplementation;
import implementations.Implementation;
import mutations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import selectors.*;
import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.RandomSubject;
import subjectModels.Subject;
import subjectModels.roles.Character;
import subjectModels.equipment.Items;
import subjectModels.roles.RandomCharacter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static subjectModels.Constants.ItemType.ARMOR;

public class Main {
    public static void main(String[] args){
        new TestMain().run();

    }
}
