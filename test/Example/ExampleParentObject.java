package Example;


import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;

public class ExampleParentObject {
    
    @TransferGrid(editable = false)
    protected String name;
    
    
    public ExampleParentObject(String name){
        this.name = name;
    }
    
}
