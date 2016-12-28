import java.util.Properties;
import javax.swing.JOptionPane;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import AgendaInterface.Agenda;
import AgendaInterface.AgendaHelper;

public class AgendaServidor {

	public AgendaServidor(POA rootPOA, NamingContext nameServer, String nomeAgenda){

		AgendaIMPL agendaIMPL = null;

		try {
			agendaIMPL = new AgendaIMPL(nomeAgenda);

			Object objRef = rootPOA.servant_to_reference(agendaIMPL);

			NameComponent[] name = {new NameComponent(nomeAgenda, "")};

			nameServer.rebind(name, objRef);

			rootPOA.the_POAManager().activate();

			System.out.println(nomeAgenda + " Pronta ...");

			if(nomeAgenda.equals("Agenda1"))
				atualizarAgendaAtual(nameServer, agendaIMPL, 2, 3);
			
			else if(nomeAgenda.equals("Agenda2"))
				atualizarAgendaAtual(nameServer, agendaIMPL, 1, 3);
			
			else if(nomeAgenda.equals("Agenda3"))
				atualizarAgendaAtual(nameServer, agendaIMPL, 1, 2);
			
		} catch (Exception e) {
			System.err.println(" Erro na " + nomeAgenda);
		}
	}

	/**
	 * Método que atualiza a agendaAtual, ou seja, a que acaba de ser inicializada de acordo com os contatos
	 * de uma agenda que já esteja em funcionamento.
	 * Esse método recebe como parâmetros o servidor de nomes, a agendaAtual(a que acaba de ser instanciada),
	 * e os índices das agendas que possivelmente já estejam em funcionamento.
	 */
	public void atualizarAgendaAtual(NamingContext nameServer, AgendaIMPL agenda, int indice1, int indice2){

		Agenda agendaA, agendaB;

		/**
		 * Obtendo a referência da AgendaA
		 */
		try {
			boolean vazia = false;

			NameComponent[] nomeAgendaA = {new NameComponent("Agenda"+ indice1, "")};
			org.omg.CORBA.Object objRef1;
			objRef1 = nameServer.resolve(nomeAgendaA);
			System.out.println("Referência da agenda"+ indice1 +" encontrada!!!");
			agendaA = AgendaHelper.narrow(objRef1);

			//Obtendo a lista de contatos da AgendaA
			String listaContatos = agendaA.consultarContatos();

			if(listaContatos.equals("Agenda Vazia!!!")){
				vazia = true;
				System.out.println("Agenda Vazia!!!");
			}

			//Atualização da Agenda Atual de acordo com os contatos da AgendaA
			if(vazia == false)
				agenda.atualizarAgenda(listaContatos);

		} catch (Exception e) {
			System.err.println("Falha na manipulação da Agenda" + indice1);

			/**
			 * Obtendo a referência da AgendaB
			 */
			try {
				boolean vazia = false;
				NameComponent[] nomeAgendaB = {new NameComponent("Agenda"+ indice2, "")};
				org.omg.CORBA.Object objRef1;
				objRef1 = nameServer.resolve(nomeAgendaB);
				System.out.println("Referência da agenda"+ indice2 +" encontrada!!!");
				agendaB = AgendaHelper.narrow(objRef1);

				//Obtendo a lista de contatos da AgendaB
				String listaContatos = agendaB.consultarContatos();

				if(listaContatos.equals("Agenda Vazia!!!")){
					vazia = true;
					System.out.println("Agenda Vazia!!!");
				}

				//Atualização da Agenda Atual de acordo com os contatos da AgendaB 
				if(vazia == false)
					agenda.atualizarAgenda(listaContatos);

			} catch (Exception e2) {
				System.err.println("Falha na manipulação da Agenda"+ indice2);
			}
		}
	}

	public static void main(String[] args) {

		NamingContext nameServer;
		String IPServidorNomes = null;
		String portaServidorNomes = null;
		String entradaIPServidorNomes = null;
		String entradaPortaServidorNomes = null;
		String nomeAgenda = null;
		String entradaNomeAgenda = null;

		/**
		 * Pegando o nome da agenda
		 */
		entradaNomeAgenda = JOptionPane.showInputDialog("Informe o nome da agenda.\nInformar Agenda1, Agenda2 ou Agenda3!!!");
		//Se o botão cancelar for clicado.
		if(entradaNomeAgenda == null){
			System.exit(0);
		}

		//Validação do nome recebido.
		else if(entradaNomeAgenda.equals("Agenda1") || entradaNomeAgenda.equals("Agenda2") || entradaNomeAgenda.equals("Agenda3"))
			nomeAgenda = entradaNomeAgenda;

		else{
			JOptionPane.showMessageDialog(null, "Nome inválido");
			System.exit(0);
		}

		/**
		 * Pegando o endereço IP do Servidor de Nomes
		 */
		entradaIPServidorNomes = JOptionPane.showInputDialog("Informe o endereço IP do Servidor de Nomes!");
		//Se o botão cancelar for clicado.
		if(entradaIPServidorNomes == null){
			System.exit(0);
		}

		//Validação do endereço recebido.
		// O formato do endereçamento é o IPV4.
		else if(entradaIPServidorNomes.equals("localhost") || entradaIPServidorNomes.matches("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}"))
			IPServidorNomes = entradaIPServidorNomes;

		else{
			JOptionPane.showMessageDialog(null, "Endereço inválido");
			System.exit(0);
		}

		/**
		 * Pegando a porta do Servidor de Nomes
		 */
		entradaPortaServidorNomes = JOptionPane.showInputDialog("Informe a porta do Servidor de Nomes!");
		//Se o botão cancelar for clicado.
		if(entradaPortaServidorNomes == null){
			System.exit(0);
		}

		//Validação do endereço recebido.
		else if(entradaPortaServidorNomes.matches("\\d{1,6}"))
			portaServidorNomes = entradaPortaServidorNomes;

		else{
			JOptionPane.showMessageDialog(null, "Porta inválida");
			System.exit(0);
		}

		Properties properties = new Properties();
		if(!(IPServidorNomes == null) && !(portaServidorNomes == null)){

			properties.put("org.omg.CORBA.ORBInitialHost", IPServidorNomes);
			properties.put("org.omg.CORBA.ORBInitialPort", portaServidorNomes);
		}

		try {
			/**
			 * Inicializando o ORB e obtendo as referências do POA e do Servidor de Nomes
			 */
			ORB orb = ORB.init(args, properties);
			Object objPoa = orb.resolve_initial_references("RootPOA");
			POA rootPOA = POAHelper.narrow(objPoa);

			Object objNameService = orb.resolve_initial_references("NameService");
			nameServer = NamingContextExtHelper.narrow(objNameService);

			new AgendaServidor(rootPOA, nameServer, nomeAgenda);

			orb.run();

		} catch (Exception e) {
			System.err.println(" Erro na agenda!!!");
			JOptionPane.showMessageDialog(null, "Erro na inicialização do ORB ou na obtenção do POA ou do Servidor de Nomes!\nTente novamente!");
		}
	}
}