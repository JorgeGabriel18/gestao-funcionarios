import br.edu.folha.repository.FuncionarioRepository;
import br.edu.folha.service.FuncionarioService;
import br.edu.folha.view.FuncionarioView;

public class Main {

    public static void main(String[] args) {
        FuncionarioRepository repository = new FuncionarioRepository();
        FuncionarioService service = new FuncionarioService(repository);
        new FuncionarioView(service, System.in, System.out).iniciar();
    }
}
