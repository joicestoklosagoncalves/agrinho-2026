

/**
 * SISTEMA AGROFUTURO 2026 - MÓDULO INTERATIVO E DE ACESSIBILIDADE
 */

document.addEventListener("DOMContentLoaded", () => {
    inicializarAccordion();
    inicializarFormularios();
    inicializarAcessibilidade();
});

/* ==========================================================================
   1. SISTEMA ACCORDION (EXPANSÍVEL)
   ========================================================================== */
function inicializarAccordion() {
    const headers = document.querySelectorAll(".accordion-header");

    headers.forEach(header => {
        header.addEventListener("click", () => {
            const panel = header.nextElementSibling;
            const isExpanded = header.getAttribute("aria-expanded") === "true";

            // Fechar outros painéis para manter visual limpo (Opcional)
            document.querySelectorAll(".accordion-header").forEach(otherHeader => {
                if (otherHeader !== header) {
                    otherHeader.setAttribute("aria-expanded", "false");
                    otherHeader.nextElementSibling.style.display = "none";
                    otherHeader.querySelector(".accordion-icon").textContent = "+";
                }
            });

            // Alternar estado do item clicado
            if (isExpanded) {
                header.setAttribute("aria-expanded", "false");
                panel.style.display = "none";
                header.querySelector(".accordion-icon").textContent = "+";
            } else {
                header.setAttribute("aria-expanded", "true");
                panel.style.display = "block";
                header.querySelector(".accordion-icon").textContent = "−";
            }
        });
    });
}

/* ==========================================================================
   2. GERENCIAMENTO DE FORMULÁRIOS E COMENTÁRIOS
   ========================================================================== */
function inicializarFormularios() {
    const formInscricao = document.getElementById("form-inscricao");
    const formComentario = document.getElementById("form-comentario");
    const listaComentarios = document.getElementById("lista-comentarios");

    // Validação e feedback do Formulário do Seminário
    formInscricao.addEventListener("submit", (e) => {
        e.preventDefault();
        const nome = document.getElementById("nome").value;
        alert(`Parabéns, ${nome}! Sua inscrição no seminário on-line do AgroFuturo 2026 foi confirmada com sucesso.`);
        formInscricao.reset();
    });

    // Fluxo de Comentários Dinâmicos
    formComentario.addEventListener("submit", (e) => {
        e.preventDefault();
        const campoTexto = document.getElementById("txt-comentario");
        const texto = campoTexto.value.trim();

        if (texto) {
            const card = document.createElement("div");
            card.classList.add("comment-card");
            card.innerHTML = `<p>${texto}</p><small style="color: var(--cor-azul-brilhante)">Enviado agora por Leitor Anônimo</small>`;
            
            listaComentarios.prepend(card);
            campoTexto.value = "";
        }
    });
}

/* ==========================================================================
   3. MOTORES DE ACESSIBILIDADE (FONTE, TEMA E SYNTHESIS VOZ)
   ========================================================================== */
function inicializarAcessibilidade() {
    let tamanhoFonteAtual = 100; // Porcentagem base
    const btnAumentar = document.getElementById("btn-aumentar");
    const btnDiminuir = document.getElementById("btn-diminuir");
    const btnTema = document.getElementById("btn-tema");
    const btnFalar = document.getElementById("btn-falar");
    const btnParar = document.getElementById("btn-parar");

    // Controle de Dimensionamento de Fonte Rem
    btnAumentar.addEventListener("click", () => {
        tamanhoFonteAtual += 10;
        document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
    });

    btnDiminuir.addEventListener("click", () => {
        if (tamanhoFonteAtual > 80) {
            tamanhoFonteAtual -= 10;
            document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
        }
    });

    // Alternador de Modo Escuro / Claro
    btnTema.addEventListener("click", () => {
        document.body.classList.toggle("light-mode");
    });

    // API SpeechSynthesis (Leitura de Voz Nativa)
    let sinteseVoz = window.speechSynthesis;
    let utterance = null;

    btnFalar.addEventListener("click", () => {
        // Captura apenas o conteúdo de texto da tag principal ignorando botões/formulários
        const mainContent = document.getElementById("conteudo-principal");
        
        // Clonamos para remover elementos indesejados da leitura de voz de forma segura
        const clone = mainContent.cloneNode(true);
        const elementosIgnorar = clone.querySelectorAll("button, form, .comment-form, script");
        elementosIgnorar.forEach(el => el.remove());

        const textoParaLer = clone.innerText;

        if (sinteseVoz.speaking) {
            sinteseVoz.cancel(); // Reinicia se já estiver lendo
        }

        utterance = new SpeechSynthesisUtterance(textoParaLer);
        utterance.lang = "pt-BR";

        utterance.onstart = () => {
            btnFalar.disabled = true;
            btnParar.disabled = false;
        };

        utterance.onend = () => {
            btnFalar.disabled = false;
            btnParar.disabled = true;
        };

        sinteseVoz.speak(utterance);
    });

    btnParar.addEventListener("click", () => {
        if (sinteseVoz.speaking) {
            sinteseVoz.cancel();
            btnFalar.disabled = false;
            btnParar.disabled = true;
        }
    });
}