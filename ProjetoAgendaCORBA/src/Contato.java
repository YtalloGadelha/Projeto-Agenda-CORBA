public class Contato {
	
	private String nomeContato = null;
	private String telefoneContato = null;
	
	public Contato(String nomeContato, String telefoneContato) {
		super();
		this.nomeContato = nomeContato;
		this.telefoneContato = telefoneContato;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}
}
