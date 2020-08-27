package edu.bsu.cs222;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//This class sorts the revision data to be used by the UI
//Code from Noah Ziems

public class RevisionSorter {
    public ArrayList<Revision> sort(ArrayList<Revision> unsortedRevisions){
        HashMap<String, ArrayList<Date>> namedRevisionsMap = new HashMap<>();
        for(Revision r: unsortedRevisions){
            if(namedRevisionsMap.containsKey(r.name))
                namedRevisionsMap.put(r.name, new ArrayList<>());
            namedRevisionsMap.get(r.name).add(r.timeStamp);
        }
        ArrayList<String> namesPartiallySorted = new ArrayList<>();
        ArrayList<String> namesUnsortedByFrequency = new ArrayList<>(namedRevisionsMap.keySet());
        while(namesUnsortedByFrequency.size() > 0){
            String mostFrequentName = null;
            int mostFrequentCount = 0;
            for(String name : namesUnsortedByFrequency){
                if(mostFrequentName == null || namedRevisionsMap.get(name).size() > mostFrequentCount){
                    mostFrequentName = name;
                    mostFrequentCount = namedRevisionsMap.get(name).size();
                }
            }
            namesPartiallySorted.add(mostFrequentName);
            namesUnsortedByFrequency.remove(mostFrequentName);
        }
        ArrayList<Revision> revisionsSortedByFrequency = new ArrayList<>();
        for(String name: namesPartiallySorted)
            for(Date date: namedRevisionsMap.get(name))
                revisionsSortedByFrequency.add(new Revision(name, date));
            return revisionsSortedByFrequency;
    }
}
