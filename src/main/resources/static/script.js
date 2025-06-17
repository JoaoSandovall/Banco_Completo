document.addEventListener('DOMContentLoaded', () => {
    // Lógica para proteger páginas e preencher dados do usuário
    const paginaAtual = window.location.pathname.split('/').pop();
    const paginasProtegidas = ['home.html', 'extrato.html', 'credit_card.html'];
    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado'));

    if (paginasProtegidas.includes(paginaAtual) && !usuarioLogado) {
        // Se tentar acessar página protegida sem login, volta para o login
        window.location.href = 'login.html';
        return;
    }

    if (paginaAtual === 'home.html' && usuarioLogado) {
        document.getElementById('welcome-message').textContent = `Olá, ${usuarioLogado.nomeCompleto}!`;
        // Exemplo de como preencher o saldo (supondo que a conta venha no objeto do usuário)
        // const saldoFormatado = usuarioLogado.conta.saldo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        // document.getElementById('balance-amount').textContent = saldoFormatado;
    }

    // --- LÓGICA DO FORMULÁRIO DE CADASTRO ---
    const formCadastro = document.getElementById('cadastro-form');
    if (formCadastro) {
        formCadastro.addEventListener('submit', async (event) => {
            event.preventDefault();
            const email = formCadastro.querySelector('input[type="email"]').value;
            const cpf = formCadastro.querySelector('input[placeholder="CPF"]').value;
            const senha = formCadastro.querySelector('input[type="password"]').value;

            try {
                const response = await fetch('http://localhost:8080/api/cadastro', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, cpf, senha })
                });

                if (response.ok) {
                    alert('Cadastro realizado com sucesso! Você será redirecionado para o login.');
                    window.location.href = 'login.html';
                } else {
                    const errorText = await response.text();
                    alert(`Erro no cadastro: ${errorText}`);
                }
            } catch (error) {
                console.error('Erro de conexão:', error);
                alert('Não foi possível conectar ao servidor. Verifique o console (F12).');
            }
        });
    }

    // --- LÓGICA DO FORMULÁRIO DE LOGIN ---
    const formLogin = document.getElementById('login-form');
    if (formLogin) {
        formLogin.addEventListener('submit', async (event) => {
            event.preventDefault();
            const cpf = document.getElementById('cpf').value;
            const senha = document.getElementById('senha').value;
            const errorMessage = document.getElementById('error-message') || { style: {}, textContent: '' };

            try {
                const response = await fetch('http://localhost:8080/api/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ cpf, senha })
                });

                if (response.ok) {
                    const usuario = await response.json();
                    localStorage.setItem('usuarioLogado', JSON.stringify(usuario));
                    window.location.href = 'home.html';
                } else {
                    errorMessage.textContent = 'CPF ou senha inválidos.';
                    errorMessage.style.display = 'block';
                }
            } catch (error) {
                console.error('Erro de conexão:', error);
                errorMessage.textContent = 'Erro ao conectar ao servidor.';
                errorMessage.style.display = 'block';
            }
        });
    }
});