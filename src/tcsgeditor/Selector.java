package tcsgeditor;

/**
 *
 * @author solkin
 */
public class Selector {
  
  private String[] array;
  private int selectedIndex;
  
  public Selector(String[] array, int selectedIndex) {
    this.array = array;
    this.selectedIndex = selectedIndex;
  }
  
  public void setArray(String[] array) {
    this.array = array;
  }
  
  public void setSelctedIndex(int selectedIndex) {
    this.selectedIndex = selectedIndex;
  }
  
  public String[] getArray() {
    return array;
  }
  
  public int getSelectedIndex() {
    return selectedIndex;
  }
}
