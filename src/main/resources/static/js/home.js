let alunoSelecionado = null;

async function cadastrarAluno() {
    const formCadastrar = document.getElementById("form-aluno");
    const messageCadastrar = document.getElementById("messageCadastrar");

    formCadastrar.addEventListener("submit", async (e) => {
        e.preventDefault();

        messageCadastrar.innerText = ``;
        messageCadastrar.className = ``;

        try {
            const formData = new FormData(formCadastrar);

            // validacao para remover espacos no inicio e final da string
            for (let [key, value] of formData.entries()) {
                if (typeof value === "string") {
                    formData.set(key, value.trim());
                }
            }

            const data = Object.fromEntries(formData.entries());

            const response = await fetch("/alunos", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error("Deu erro");
            }

            messageCadastrar.innerText = `Aluno cadastrado com sucesso!`;
            messageCadastrar.className = `success`;

            formCadastrar.reset();
            await carregarAlunos();
        } catch (error) {
            console.log(error)
        }
    })
}

async function carregarAlunos() {
    try {
        const response = await fetch("http://localhost:8080/alunos", {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error("Deu erro");
        }

        const alunos = await response.json();

        const tbodyAlunos = document.getElementById("tabela-alunos-body");
        tbodyAlunos.innerHTML = "";

        const alunosFragment = document.createDocumentFragment();

        alunos.forEach(aluno => {
            const tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${aluno.id}</td>
                <td>${aluno.nome}</td>
                <td>${aluno.dataNascimento}</td>
                <td>${aluno.email}</td>
                <td>${aluno.telefone}</td>
                <td>
                    <button>Deletar</button>
                    <button>Editar</button>
                </td>
            `;

            alunosFragment.appendChild(tr);
        })

        tbodyAlunos.appendChild(alunosFragment);
    } catch (error) {
        console.log(error);
    }
}

// funcoes modal de exclusao
function abrirModalExclusao(aluno) {
    alunoSelecionado = aluno;

    document.getElementById("modal-nome-aluno").textContent = aluno.nome;

    const modal = document.getElementById("modal-confirmacao");
    modal.hidden = false;
}

function fecharModalExclusao() {
    alunoSelecionado = null;

    const modal = document.getElementById("modal-confirmacao");
    modal.hidden = true;
}

document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();
    cadastrarAluno();
})