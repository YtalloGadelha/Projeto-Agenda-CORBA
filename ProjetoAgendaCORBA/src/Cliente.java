import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import AgendaInterface.Agenda;
import AgendaInterface.AgendaHelper;

@SuppressWarnings("serial")
public class Cliente extends JFrame {

	public JButton botaoInserir,botaoExcluir,botaoConsultar,botaoAtualizar;
	public JTextField textNomeRecebido,textTelefoneRecebido;
	public JTextArea textNome,textTelefone, textInfoAgenda;
	public JScrollPane scrollNome, scrollTelefone, scrollInfoAgenda;
	private Agenda agendaAtual, agenda1, agenda2, agenda3;
	private String agendaAtualNome = null;
	private NamingContext servidorNomes;

	public Cliente(NamingContext servidorNomes) {
		this.setServidorNomes(servidorNomes);

		configurarConexao();

		/**
		 * Configuração dos botões
		 */
		botaoInserir = new JButton("Inserir");
		botaoInserir.addActionListener(new BotaoInserirListerner());
		configurarBotao(botaoInserir);

		botaoExcluir = new JButton("Excluir");
		botaoExcluir.addActionListener(new BotaoExcluirListerner());
		configurarBotao(botaoExcluir);

		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.addActionListener(new BotaoAtualizarListener());
		configurarBotao(botaoAtualizar);

		botaoConsultar = new JButton("Consultar");
		botaoConsultar.addActionListener(new BotaoConsultarListener());
		configurarBotao(botaoConsultar);

		/**
		 * Configuração dos textFields
		 */
		textNomeRecebido = new JTextField("");
		configurarTextField(textNomeRecebido);

		textTelefoneRecebido = new JTextField();
		configurarTextField(textTelefoneRecebido);

		/**
		 * Configuração dos textArea
		 */
		textNome = new JTextArea("Nome:");
		configurarTextArea(textNome);

		textTelefone = new JTextArea("Telefone:");
		configurarTextArea(textTelefone);

		textInfoAgenda = new JTextArea();
		textInfoAgenda.setBackground(new Color(255, 250, 240));
		configurarTextArea(textInfoAgenda);

		/**
		 * Configuração dos scrolls
		 */
		scrollNome = new JScrollPane(textNome);
		scrollTelefone = new JScrollPane(textTelefone);
		scrollInfoAgenda = new JScrollPane(textInfoAgenda);
		configurarPoliticasScroll(scrollInfoAgenda);

		//Container com os botões da GUI
		Container botoes;
		botoes = new JPanel();
		botoes.setLayout(new GridLayout());
		botoes.add(botaoInserir,new Point(0,0));
		botoes.add(botaoExcluir,new Point(0,1));
		botoes.add(botaoAtualizar, new Point(0,2));
		botoes.add(botaoConsultar, new Point(0,3));

		//Container com os campos para nome e telefone(incluindo os campos de inserção de dados)
		Container dados = new JPanel();
		GridLayout gl_nome = new GridLayout(2,2);
		dados.setLayout(gl_nome);
		dados.add(scrollNome);
		dados.add(textNomeRecebido);
		dados.add(scrollTelefone);
		dados.add(textTelefoneRecebido);

		//Container principal que contém os botões, a área para visualização da agenda e os campos de dados.
		Container principal = getContentPane();
		principal.setLayout(new BorderLayout(0, 6));
		principal.add(botoes, BorderLayout.NORTH);
		principal.add(scrollInfoAgenda, BorderLayout.CENTER);
		principal.add(dados, BorderLayout.SOUTH);

		/**
		 * Configurações da Janela
		 */
		setResizable(false);
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Método que configura as políticas do Scrool
	 */
	public void configurarPoliticasScroll(JScrollPane scroll){

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	/**
	 * Método que configura os botões
	 */
	public void configurarBotao(JButton botao){

		botao.setBackground(new Color(255, 255, 255));
		botao.setForeground(new Color(31, 58, 147));
		botao.setFont(new Font("Serif", Font.PLAIN, 22));
	}

	/**
	 * Método que configura as textAreas
	 */
	public void configurarTextArea(JTextArea textArea){
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.PLAIN, 22));
	}

	/**
	 * Método que configura os textFields
	 */
	public void configurarTextField(JTextField textField){

		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setEditable(true);
		textField.setBackground(new Color(255, 255, 255));
		textField.setForeground(Color.DARK_GRAY);
		textField.setFont(new Font("Serif", Font.PLAIN, 20));
	}

	/**
	 * Método que configura a conexão com as agendas.
	 * Nesse método, a primeira agenda procurada é a Agenda1.
	 * Caso não esteja disponível, a Agenda2 é buscada e assim por diante.
	 * Caso nenhuma agenda esteja disponível, aparecerá um aviso informando.  
	 */
	public void configurarConexao(){

		//Tentando se conectar a AGENDA1
		try {

			NameComponent[] nomeAgenda1 = {new NameComponent("Agenda1", "")};
			org.omg.CORBA.Object objRef;
			objRef = servidorNomes.resolve(nomeAgenda1);
			System.out.println("Referência da agenda1 encontrada!!!");
			agenda1 = AgendaHelper.narrow(objRef);
			this.agendaAtual = agenda1;
			this.agendaAtualNome = agendaAtual.informarNomeAgenda();

		} catch (Exception e) {
			System.out.println("Erro ao tentar conectar a agenda1");
			System.out.println("Tentando agora se conectar a agenda2");

			//Tentando se conectar a AGENDA2
			try {

				NameComponent[] nomeAgenda2 = {new NameComponent("Agenda2", "")};
				org.omg.CORBA.Object objRef;
				objRef = servidorNomes.resolve(nomeAgenda2);
				System.out.println("Referência da agenda2 encontrada!!!");
				agenda2 = AgendaHelper.narrow(objRef);
				this.agendaAtual = agenda2;
				this.agendaAtualNome = agendaAtual.informarNomeAgenda();

			} catch (Exception e2) {
				System.out.println("Erro ao tentar conectar a agenda2");
				System.out.println("Tentando agora se conectar a agenda3");

				//Tentando se conectar a AGENDA3
				try {

					NameComponent[] nomeAgenda3 = {new NameComponent("Agenda3", "")};
					org.omg.CORBA.Object objRef;
					objRef = servidorNomes.resolve(nomeAgenda3);
					System.out.println("Referência da agenda3 encontrada!!!");
					agenda3 = AgendaHelper.narrow(objRef);
					this.agendaAtual = agenda3;
					this.agendaAtualNome = agendaAtual.informarNomeAgenda();

				} catch (Exception e3) {
					System.out.println("Erro ao tentar conectar a agenda3");
					agendaAtualNome = "Nenhuma Agenda Disponível";
					JOptionPane.showMessageDialog(null, "Nenhuma Agenda Disponível!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					textInfoAgenda.setText("");
				}
			}
		}
		setTitle(agendaAtualNome);
	}

	/**
	 * 	Método que atualiza os contatos das demais agendas de acordo com os contatos da Agenda Atual.
	 * 	O parâmetro recebido(indice) informa qual agenda deve ser atualizada.
	 */
	public void atualizarAgenda(int indice){

		try {
			Agenda agendaAtualiza;
			NameComponent[] nomeAgenda = {new NameComponent("Agenda" + indice, "")};
			org.omg.CORBA.Object objRef;
			objRef = servidorNomes.resolve(nomeAgenda);
			System.out.println("Referência da agenda" + indice + " encontrada!!!");
			agendaAtualiza = AgendaHelper.narrow(objRef);

			String listaContatos = agendaAtual.consultarContatos();

			agendaAtualiza.atualizarAgenda(listaContatos);

		} catch (Exception e2) {
			System.out.println("Não foi possível atualizar Agenda" + indice);
		}
	}

	/**
	 * Método chamado quando o botão inserir for clicado.
	 * Nesse método, os dados dos campos nome e telefone são obtidos
	 * e passados como parâmetros para o método inserir contato.
	 */
	public class BotaoInserirListerner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String nome = null, telefone = null, msg = null;

			try {

				nome = textNomeRecebido.getText();
				telefone = textTelefoneRecebido.getText();

				//Tratamento da inserção de dados vazios
				if(nome.isEmpty() || nome.trim().isEmpty() || telefone.isEmpty() || telefone.trim().isEmpty()){
					textInfoAgenda.setText("Dados inválidos! Nome ou telefone vazio!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}
				
				/**
				 * Verifico se o usuário entrou com caracteres inválidos no nome(":";").
				 * Esses caracteres utilizo em algum momento no split e pode gerar problemas, 
				 * caso sejam utilizados pelo usuário.
				 */
				if(nome.matches("^;.*") || nome.matches(".+;.+") || nome.matches(".*;$") || nome.matches("^:.*")|| nome.matches(".+:.+") || nome.matches(".*:$")){
					textInfoAgenda.setText("Nome com caracteres inválidos. Não utilize : ou ;");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}
				
				/**
				 * Verifico se o usuário entrou com texto no campo telefone.
				 * Caso tenha entrado, uma mensagem informando o erro é mostrada na área de informações da agenda.
				 */
				try {
					//Verificando conversão de string para número
					Long.parseLong(telefone);
					//System.out.println(Long.parseLong(telefone));
				} catch (Exception e2) {
					textInfoAgenda.setText("Telefone inválido!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}

				msg = agendaAtual.inserirContato(nome, telefone);
				textNomeRecebido.setText("");
				textTelefoneRecebido.setText("");
				textInfoAgenda.setText(msg);

				if(agendaAtualNome.equals("Agenda1")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(2);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda2")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda3")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(2);
				}

			} catch (Exception a) {
				//Caso a agendaAtual não esteja mais no ar, uma outra agenda é buscada.
				System.err.println("Falha na inserção");
				configurarConexao();

				try {

					msg = agendaAtual.inserirContato(nome, telefone);
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					textInfoAgenda.setText(msg);

					if(agendaAtualNome.equals("Agenda1")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(2);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda2")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda3")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(2);
					}
				} catch (Exception e2) {
					System.err.println("Falha na inserção");
				}
			}
		}
	}

	/**
	 * Método chamado quando o botão excluir for clicado.
	 * Nesse método, o dado do campo nome
	 * e passado como parâmetro para o método remover contato.
	 */
	public class BotaoExcluirListerner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String nome = null, msg = null;
			
			try {

				nome = textNomeRecebido.getText();

				//Tratamento da inserção de dado vazio
				if(nome.isEmpty() || nome.trim().isEmpty()){
					textInfoAgenda.setText("Nome vazio!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}

				msg = agendaAtual.removerContato(nome);
				textNomeRecebido.setText("");
				textTelefoneRecebido.setText("");
				textInfoAgenda.setText(msg);

				if(agendaAtualNome.equals("Agenda1")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(2);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda2")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda3")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(2);
				}

			} catch (Exception a) {
				//Caso a agendaAtual não esteja mais no ar, uma outra agenda é buscada.
				System.err.println("Falha na remoção");
				configurarConexao();
				
				try {
					msg = agendaAtual.removerContato(nome);
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					textInfoAgenda.setText(msg);

					if(agendaAtualNome.equals("Agenda1")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(2);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda2")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda3")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(2);
					}
				} catch (Exception e2) {
					System.err.println("Falha na remoção");
				}
			}
		}
	}

	/**
	 * Método chamado quando o botão atualizar for clicado.
	 * Nesse método, os dados dos campos nome e telefone são obtidos
	 * e passados como parâmetros para o método inserir contato.
	 * Vale ressaltar que a atualização pode ser referente ao nome
	 * ou ao telefone. Caso seja desejado alterar o número de alguém,
	 * o novo telefone deve ser inserido "juntamente" com o nome correto. Caso seja desejado
	 * alterar o nome de alguém, o novo nome deve ser inserido "juntamente" com o telefone correto. 
	 */
	public class BotaoAtualizarListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String nome = null, telefone = null, msg = null;
			
			try {

				nome = textNomeRecebido.getText();
				telefone = textTelefoneRecebido.getText();

				//Tratamento da inserção de dados vazios
				if(nome.isEmpty() || nome.trim().isEmpty() || telefone.isEmpty() || telefone.trim().isEmpty()){
					textInfoAgenda.setText("Dados inválidos! Nome ou telefone vazio!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}
				
				/**
				 * Verifico se o usuário entrou com caracteres inválidos no nome(":";").
				 * Esses caracteres utilizo em algum momento no split e pode gerar problemas, 
				 * caso sejam utilizados pelo usuário.
				 */
				if(nome.matches("^;.*") || nome.matches(".+;.+") || nome.matches(".*;$") || nome.matches("^:.*")|| nome.matches(".+:.+") || nome.matches(".*:$")){
					textInfoAgenda.setText("Nome com caracteres inválidos. Não utilize : ou ;");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}
				
				/**
				 * Verifico se o usuário entrou com texto no campo telefone.
				 * Caso tenha entrado, uma mensagem informando o erro é mostrada na área de informações da agenda.
				 */
				try {
					//Verificando conversão de string para número
					Long.parseLong(telefone);
					//System.out.println(Long.parseLong(telefone));
				} catch (Exception e2) {
					textInfoAgenda.setText("Telefone inválido!!!");
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					return;
				}

				msg = agendaAtual.atualizarContato(nome, telefone);
				textNomeRecebido.setText("");
				textTelefoneRecebido.setText("");
				textInfoAgenda.setText(msg);

				if(agendaAtualNome.equals("Agenda1")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(2);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda2")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(3);
				}

				else if(agendaAtualNome.equals("Agenda3")){

					//Atualizando os contatos para as demais agendas
					atualizarAgenda(1);
					atualizarAgenda(2);
				}

			} catch (Exception a) {
				//Caso a agendaAtual não esteja mais no ar, uma outra agenda é buscada.
				System.err.println("Falha na atualização do contato.");
				configurarConexao();
				
				try {
					msg = agendaAtual.atualizarContato(nome, telefone);
					textNomeRecebido.setText("");
					textTelefoneRecebido.setText("");
					textInfoAgenda.setText(msg);

					if(agendaAtualNome.equals("Agenda1")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(2);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda2")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(3);
					}

					else if(agendaAtualNome.equals("Agenda3")){

						//Atualizando os contatos para as demais agendas
						atualizarAgenda(1);
						atualizarAgenda(2);
					}
				} catch (Exception e2) {
					System.err.println("Falha na atualização do contato.");
				}
			}
		}
	}

	/**
	 * Método chamado quando o botão consultar for clicado.
	 * Nesse método, a lista com o contatos da agenda é mostrada 
	 * na área referente as informações da agenda.
	 */
	public class BotaoConsultarListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String msg;

			try {

				msg = agendaAtual.consultarContatos();

				if(msg.equals("Agenda Vazia!!!")){
					textInfoAgenda.setText(agendaAtualNome + " Vazia!!!");
					return;
				}

				textInfoAgenda.setText(agendaAtualNome + " - Lista de Contatos" + "\n\n");

				//Separação da string recebida para a obtenção dos contatos.
				String[] parts = msg.split(";");

				for (String string : parts) {
					textInfoAgenda.append(string + "\n");
				}

			} catch (Exception a) {
				//Caso a agendaAtual não esteja mais no ar, uma outra agenda é buscada.
				System.err.println("Falha na consulta");
				configurarConexao();

				try {
					msg = agendaAtual.consultarContatos();

					if(msg.equals("Agenda Vazia!!!")){
						textInfoAgenda.setText(agendaAtualNome + " Vazia!!!");
						return;
					}

					textInfoAgenda.setText(agendaAtualNome + " - Lista de Contatos" + "\n\n");

					//Separação da string recebida para a obtenção dos contatos.
					String[] parts = msg.split(";");

					for (String string : parts) {
						textInfoAgenda.append(string + "\n");
					}

				} catch (Exception e2) {
					System.err.println("Falha na consulta");
				}
			}
		}
	}

	public NamingContext getServidorNomes() {
		return servidorNomes;
	}

	public void setServidorNomes(NamingContext servidorNomes) {
		this.servidorNomes = servidorNomes;
	}

	public static void main(String[] args) {

		NamingContext servidorNomes = null;

		String IPServidorNomes = null;
		String portaServidorNomes = null;
		String entradaIPServidorNomes = null;
		String entradaPortaServidorNomes = null;

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
			 * Inicializando o ORB e obtendo a referência do Servidor de Nomes
			 */
			ORB orb = ORB.init(args,properties);

			org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
			servidorNomes = NamingContextHelper.narrow(obj);
			System.out.println("Servidor de nomes encontrado!");

		}catch (Exception e) { 
			System.out.println("Erro na inicialização do ORB ou na obtenção do Servidor de Nomes!!!") ;
			JOptionPane.showMessageDialog(null, "Erro na inicialização do ORB ou na obtenção do Servidor de Nomes!\nTente novamente!");
			return;
		}

		new Cliente(servidorNomes);
	}
}