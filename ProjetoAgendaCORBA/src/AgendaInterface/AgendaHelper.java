package AgendaInterface;


/**
* AgendaInterface/AgendaHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from AgendaInterface.idl
* Segunda-feira, 12 de Setembro de 2016 15h16min04s BRT
*/

abstract public class AgendaHelper
{
  private static String  _id = "IDL:AgendaInterface/Agenda:1.0";

  public static void insert (org.omg.CORBA.Any a, AgendaInterface.Agenda that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static AgendaInterface.Agenda extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (AgendaInterface.AgendaHelper.id (), "Agenda");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static AgendaInterface.Agenda read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_AgendaStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, AgendaInterface.Agenda value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static AgendaInterface.Agenda narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof AgendaInterface.Agenda)
      return (AgendaInterface.Agenda)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      AgendaInterface._AgendaStub stub = new AgendaInterface._AgendaStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static AgendaInterface.Agenda unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof AgendaInterface.Agenda)
      return (AgendaInterface.Agenda)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      AgendaInterface._AgendaStub stub = new AgendaInterface._AgendaStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
