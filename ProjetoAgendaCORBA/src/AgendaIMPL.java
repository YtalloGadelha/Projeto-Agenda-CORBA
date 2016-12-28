import java.util.ArrayList;
import AgendaInterface.AgendaPOA;

public class AgendaIMPL extends AgendaPOA{

	private ArrayList<Contato> contatosAgenda = new ArrayList<Contato>();
	String agendaNome = null;

	public AgendaIMPL(String agendaNome) {
		super();
		this.agendaNome = agendaNome;
	}

	/**
	 * Método chamado quando um contato vai ser adicionado. 
	 * Esse método recebe o nome e o telefone do contato e retorna uma mensagem 
	 * informando se a operação foi bem sucedida.
	 */
	@Override
	public String inserirContato(String nome, String telefone) {

		//booleano que indica a existência de um contato
		boolean contatoExistente = false;

		for (Contato contato : contatosAgenda) {
			if(contato.getNomeContato().equals(nome)){
				contatoExistente = true;

				return "Contato já existente!!!";
			}
		}

		if(contatoExistente == false){
			contatosAgenda.add(new Contato(nome, telefone));
		}

		return "Contato adicionado!!!";
	}

	/**
	 * Método chamado quando um contato vai ser removido. 
	 * Esse método recebe o nome do contato e retorna uma mensagem 
	 * informando se a operação foi bem sucedida.
	 */
	@Override
	public String removerContato(String nome) {

		for (Contato contato : contatosAgenda) {
			if(contato.getNomeContato().equals(nome)){
				int indice = contatosAgenda.indexOf(contato);
				contatosAgenda.remove(indice);
				return "Contato removido!!!";
			}
		}

		return "Contato não existente!!!";
	}

	/**
	 * Método chamado quando um contato vai ser atualizado. 
	 * Esse método recebe o nome e o telefone do contato e retorna uma mensagem 
	 * informando se a operação foi bem sucedida.
	 */
	@Override
	public String atualizarContato(String nome, String telefone) {

		for (Contato contato : contatosAgenda) {

			//Atualiza a partir de um nome
			if(contato.getNomeContato().equals(nome)){
				int indice = contatosAgenda.indexOf(contato);
				contatosAgenda.remove(indice);
				contatosAgenda.add(indice, new Contato(nome, telefone));

				return "Contato atualizado!!!";
			}
		}

		for (Contato contato : contatosAgenda) {
			
			//Atualiza a partir de um telefone
			if(contato.getTelefoneContato().equals(telefone)){
				int indice = contatosAgenda.indexOf(contato);
				contatosAgenda.remove(indice);
				contatosAgenda.add(indice, new Contato(nome, telefone));

				return "Contato atualizado!!!";
			}
		}

		return "Contato não existente!!!";
	}

	/**
	 * Método chamado quando a lista de contatos vai ser consultada. 
	 * Esse método retorna uma string que contém todos os contatos da agenda.
	 */
	@Override
	public String consultarContatos() {
		String contato = "";

		if(contatosAgenda.isEmpty()){
			return "Agenda Vazia!!!";
		}

		for (Contato contato1 : contatosAgenda) {
			contato += contato1.getNomeContato() + ":" + contato1.getTelefoneContato()+";";
		}
		return contato;
	}

	/**
	 * Método que informa o nome da agenda.
	 */
	@Override
	public String informarNomeAgenda() {

		return agendaNome;
	}

	/**
	 * Método que atualiza uma determinada agenda.
	 * Esse método recebe uma string que contém todos os contatos de uma agenda, 
	 * remove todos os contatos que já existem e adiciona novamente os contatos de
	 * acordo com a string recebida. 
	 */
	@Override
	public void atualizarAgenda(String contatos) {

		contatosAgenda.removeAll(contatosAgenda);

		String[] listaContatos = contatos.split(";");

		for (String partesContato : listaContatos) {
			String[] separacao = partesContato.split(":");

			inserirContato(separacao[0], separacao[1]);
		}
		System.err.println("Agenda Atualizada!!!");
	}
}
