package finishCriteria;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;

public class StructureCriteria implements FinishCriteria{
    List<Subject> previewGen = null;
    int percentage;
    int limitGen;
    int currGens = 0;

    public StructureCriteria(int percentage, int limitGen) {
        this.percentage = percentage;
        this.limitGen = limitGen;
    }

    @Override
    public boolean shouldFinish(List<Subject> subjects) {
        if(previewGen!=null){
            int subjCount = 0;
            for (Subject subject: subjects) {
                if(previewGen.contains(subject)){
                    subjCount++;
                    if(subjCount>percentage){
                        currGens++;
                        previewGen = subjects;
                        return currGens>=limitGen;
                    }
                }
            }
            previewGen=subjects;
            if(subjCount<percentage){
                currGens=0;
                return false;
            }else{
                currGens++;
                return currGens>=limitGen;
            }
        }else{
            previewGen = subjects;
            currGens = 0;
            return false;
        }
    }
}
