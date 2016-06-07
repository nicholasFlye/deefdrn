package nrd.waco.nrdfeed;

/**
 * Created by XxFLY on 3/5/2016.
 */

import java.util.ArrayList;

public class NrdListGroup {

        private String Name;
        private ArrayList<ExpandableNrdList> Items;

        public String getName() {
            return Name;
        }
        public void setName(String name) {
            this.Name = name;
        }
        public ArrayList<ExpandableNrdList> getItems() {
            return Items;
        }
        public void setItems(ArrayList<ExpandableNrdList> Items) {
            this.Items = Items;
        }
}
