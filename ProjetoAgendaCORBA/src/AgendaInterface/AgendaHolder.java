package AgendaInterface;

/**
* AgendaInterface/AgendaHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from AgendaInterface.idl
* Segunda-feira, 12 de Setembro de 2016 15h16min04s BRT
*/

public final class AgendaHolder implements org.omg.CORBA.portable.Streamable
{
  public AgendaInterface.Agenda value = null;

  public AgendaHolder ()
  {
  }

  public AgendaHolder (AgendaInterface.Agenda initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = AgendaInterface.AgendaHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    AgendaInterface.AgendaHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return AgendaInterface.AgendaHelper.type ();
  }

}
