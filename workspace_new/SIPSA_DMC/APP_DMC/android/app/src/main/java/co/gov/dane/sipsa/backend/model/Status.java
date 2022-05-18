package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

public class Status implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public enum StatusType
    {
	OK, ERROR
    }

    private StatusType type;
    private String description;

    public StatusType getType()
    {
        return type;
    }

    public void setType( StatusType type )
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Status()
    {
	type = StatusType.OK;
    }

}
