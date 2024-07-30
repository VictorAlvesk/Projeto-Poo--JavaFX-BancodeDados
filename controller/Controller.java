package controller;

import model.Database;
import model.Passagem1;
import model.PassagemRepository;
import view.View;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.TableView;

/**
 *  Controller
 *   
 * @author Matheus Teles
 * @author João Vitor
 * @author Victor Alves
 * @author Yan Santos
 * @author Gabriel Eduardo
 */
public class Controller implements Initializable {
    @FXML
    private Label labelMensagem;
    
    @FXML
    private Button buttonAtualizarID;
    @FXML
    private Button buttonBuscarID;
    @FXML
    private Button buttonDeletarID;
    @FXML
    private Button buttonLimpar;
    @FXML
    private Button buttonRegistrar;
    
    @FXML
    private TableView<Passagem1> table;
    @FXML
    private TableColumn<Passagem1, String> columnDescricao;
    @FXML
    private TableColumn<Passagem1, Integer> columnId;
    @FXML
    private TableColumn<Passagem1, String> columnNome;
    @FXML
    private TableColumn<Passagem1, Float> columnPreco;

    @FXML
    private TextArea descricao;
    @FXML
    private TextField id;
    @FXML
    private TextField nome;
    @FXML
    private TextField preco;
    
    Database database = new Database("databaseServicos");
    PassagemRepository servicoRepository = new PassagemRepository(database);
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
    }
    /**
     * Limpa a mensagem de texto com o botão "Limpar"
     */
    public void limparLabel(){
        labelMensagem.setText("");
    }
    /**
     * Limpa todos os campos de preenchimento
     */
    public void buttonLimparClicked(){
        descricao.setText("");
        id.setText("");
        nome.setText("");
        preco.setText("");
        limparLabel();
    }
    /**
     * Limpa os campos de preenchimento, exceto o ID
     */
    public void limparInfo(){
        descricao.setText("");
        nome.setText("");
        preco.setText("");
        limparLabel();
    }
    
    ObservableList<Passagem1> servicos;
    /**
     * Carrega os dados do banco de dados e os exibe na tabela
     */
    public void carregarDados(){
        servicos = FXCollections.observableArrayList(servicoRepository.loadAll());
        columnId.setCellValueFactory(new PropertyValueFactory<Passagem1, Integer>("Código"));
        columnNome.setCellValueFactory(new PropertyValueFactory<Passagem1, String>("nome"));
        columnDescricao.setCellValueFactory(new PropertyValueFactory<Passagem1, String>("CPF"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<Passagem1, Float>("preco da passagem"));
        table.setItems(servicos);
    }
    /**
     * Lê os dados dos campos de texto e os atribui a uma instância de
     * Servico e a salva no banco de dados. Também atualiza a tabela.
     */
    public void buttonRegistrarClicked(){
    
        try{
            String description = descricao.getText();
            String name = nome.getText();
            float price = 0;
            price = Float.parseFloat(preco.getText());
            if(description=="" || name=="" || price<=0){
                labelMensagem.setText("Dados inválidos ou incompletos!");
            }
            else{
                database.getConnection();
                Passagem1 servico = new Passagem1(name, description, price);
                labelMensagem.setText("");
                servicoRepository.create(servico);
                database.close();
                carregarDados();
                labelMensagem.setText("Serviço Cadastrado com sucesso!");
            }
        } catch (Exception e){
            labelMensagem.setText("Dados inválidos ou incompletos!");
        }
    }
    /**
     * Lê o campo de ID e deleta a instância de Servico no banco de dados que possui esse ID.
     * Atualiza a tabela.
     */
    public void buttonDeletarIDClicked(){
        try{
            int idAtual = Integer.parseInt(id.getText());
            if(idAtual<=0){
                labelMensagem.setText("Informe um ID válido!");
            }
            else{
                for(int i=servicos.size() ; i>0; i--){
                if(idAtual == servicos.get(i-1).getId()){
                    i=0;
                    servicoRepository.deleteById(idAtual);
                    buttonLimparClicked();
                    labelMensagem.setText("Serviço Deletado com Sucesso!");
                    carregarDados();
                }
                else{
                    labelMensagem.setText("Serviço não Encontrado!");
                }
                }
            }
        } catch (Exception e){
            labelMensagem.setText("Informe um ID válido!");
        }
    }
    /**
     * Lê o campo de ID e exibe nos campos de texto os dados da instância de Servico
     * associada a esse ID. Atualiza a tabela.
     */
    public void carregarID(){
        try{
            int idAtual = Integer.parseInt(id.getText());
            if(idAtual<=0){
                limparInfo();
            }
            else{
                try{
                    database.getConnection();
                    Passagem1 servico = servicoRepository.loadFromId(idAtual);
                    nome.setText(servico.getNome());
                    descricao.setText(servico.getDescricao());
                    preco.setText(String.valueOf(servico.getPreco()));
                    database.close();
                } catch (Exception e){
                    limparInfo();
                }
            }
        } catch (Exception e){
            limparInfo();
        }
        
    }
    /**
     * Lê os dados dos campos de texto e os atribui a uma instância de Servico. Se existir uma
     * outra no banco de dados com o mesmo código, é substituída por ela.
     */
    public void atualizarID(){
        try{
            int idAtual = Integer.parseInt(id.getText());
            String newName = nome.getText();
            String newDescricao = descricao.getText();
            float newPreco = Float.parseFloat(preco.getText());
            database.getConnection();
            Passagem1 teste = servicoRepository.loadFromId(idAtual);
            Passagem1 newServico = new Passagem1(newName, newDescricao, newPreco);
            newServico.setId(idAtual);
            servicoRepository.update(newServico);
            carregarDados();
            database.close();
            labelMensagem.setText("Código de Viagem Atualizado Com Sucesso!");
        } catch (Exception e){
            labelMensagem.setText("Código de Viagem Não Encontrado!");
        }
    }
}