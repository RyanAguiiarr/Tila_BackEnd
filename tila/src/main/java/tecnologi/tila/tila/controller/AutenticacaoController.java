package tecnologi.tila.tila.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tecnologi.tila.tila.dto.DadosAutenticacao;
import tecnologi.tila.tila.dto.DadosCadastroMedico;
import tecnologi.tila.tila.dto.DadosTokenJWT;
import tecnologi.tila.tila.entity.LogAuditoria;
import tecnologi.tila.tila.entity.Medico;
import tecnologi.tila.tila.entity.Usuario;
import tecnologi.tila.tila.enuns.PerfilUser;
import tecnologi.tila.tila.repository.LogAuditoriaRepository;
import tecnologi.tila.tila.repository.MedicoRepository;
import tecnologi.tila.tila.repository.UsuarioRepository;
import tecnologi.tila.tila.service.TokenService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogAuditoriaRepository logAuditoriaRepository;

    @PostMapping("/registrar")
    public ResponseEntity registrarMedico(@RequestBody @Valid DadosCadastroMedico dados){

        String senhaCriptografada = passwordEncoder.encode(dados.senha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(dados.email());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setPerfil(PerfilUser.MEDICO);
        usuarioRepository.save(novoUsuario);

        Medico novoMedico = new Medico();
        novoMedico.setNomeCompleto(dados.nome());
        novoMedico.setCrm(dados.crm());
        novoMedico.setEspecialidade(dados.especialidade());
        novoMedico.setUsuario(novoUsuario);
        medicoRepository.save(novoMedico);

        LogAuditoria log = new LogAuditoria();
        log.setUsuario(novoUsuario);
        log.setAcao("CADASTRO_NOVO_MEDICO");
        log.setDataHora(LocalDateTime.now());
        logAuditoriaRepository.save(log);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity efetuarlogin(@RequestBody @Valid DadosAutenticacao dados){

        var authenticationToke = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToke);

        var usuario = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuario);

        LogAuditoria log = new LogAuditoria();
        log.setUsuario(usuario);
        log.setAcao("LOGIN_SUCESSO");
        log.setDataHora(LocalDateTime.now());
        logAuditoriaRepository.save(log);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
