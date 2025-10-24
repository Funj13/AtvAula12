public class Atvaula12 {
  
    enum TipoPedido {
        NORMAL,
        PRIORITARIO,
        URGENTE
    }

 
    static class Pedido {
        private int idPedido;
        private String nomeCliente;
        private String item;
        private int quantidade;
        private double valor;
        private TipoPedido tipo;
        private Pedido proximo;

        public Pedido(int idPedido, String nomeCliente, String item, int quantidade, double valor, TipoPedido tipo) {
            this.idPedido = idPedido;
            this.nomeCliente = nomeCliente;
            this.item = item;
            this.quantidade = quantidade;
            this.valor = tipo == TipoPedido.URGENTE ? valor * 1.2 : 
                        tipo == TipoPedido.PRIORITARIO ? valor * 1.1 : valor;
            this.tipo = tipo;
            this.proximo = null;
        }
    }


    static class ListaPedidos {
        private Pedido cabeca;
        private int tamanho;

        public ListaPedidos() {
            this.cabeca = null;
            this.tamanho = 0;
        }


        public boolean estaVazia() {
            return cabeca == null;
        }


        public int tamanho() {
            return tamanho;
        }


        public Pedido getCabeca() {
            return cabeca;
        }


        public Pedido getCauda() {
            if (estaVazia()) return null;
            Pedido atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            return atual;
        }


        private boolean existePedido(int idPedido) {
            Pedido atual = cabeca;
            while (atual != null) {
                if (atual.idPedido == idPedido) return true;
                atual = atual.proximo;
            }
            return false;
        }


        public boolean inserirPedido(Pedido novoPedido) {

            if (existePedido(novoPedido.idPedido)) {
                System.out.println("Pedido duplicado: ID " + novoPedido.idPedido);
                return false;
            }

            switch (novoPedido.tipo) {
                case URGENTE:
                    inserirInicio(novoPedido);
                    break;
                case PRIORITARIO:
                    inserirMeio(novoPedido);
                    break;
                default:
                    inserirFim(novoPedido);
            }
            return true;
        }


        private void inserirInicio(Pedido novoPedido) {
            novoPedido.proximo = cabeca;
            cabeca = novoPedido;
            tamanho++;
        }


        private void inserirFim(Pedido novoPedido) {
            if (estaVazia()) {
                cabeca = novoPedido;
            } else {
                Pedido atual = cabeca;
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = novoPedido;
            }
            tamanho++;
        }


        private void inserirMeio(Pedido novoPedido) {
            if (estaVazia() || tamanho == 1) {
                inserirFim(novoPedido);
                return;
            }

            int posicaoMeio = tamanho / 2;
            Pedido anterior = null;
            Pedido atual = cabeca;
            
            for (int i = 0; i < posicaoMeio; i++) {
                anterior = atual;
                atual = atual.proximo;
            }

            if (anterior == null) {
                novoPedido.proximo = cabeca;
                cabeca = novoPedido;
            } else {
                novoPedido.proximo = atual;
                anterior.proximo = novoPedido;
            }
            tamanho++;
        }


        public Pedido removerInicio() {
            if (estaVazia()) return null;
            
            Pedido removido = cabeca;
            cabeca = cabeca.proximo;
            tamanho--;
            return removido;
        }

 
        public Pedido removerFim() {
            if (estaVazia()) return null;
            if (cabeca.proximo == null) {
                Pedido removido = cabeca;
                cabeca = null;
                tamanho--;
                return removido;
            }

            Pedido anterior = cabeca;
            Pedido atual = cabeca.proximo;
            while (atual.proximo != null) {
                anterior = atual;
                atual = atual.proximo;
            }
            anterior.proximo = null;
            tamanho--;
            return atual;
        }


        public Pedido removerPosicao(int k) {
            if (estaVazia() || k >= tamanho || k < 0) return null;
            
            if (k == 0) return removerInicio();
            
            Pedido anterior = null;
            Pedido atual = cabeca;
            
            for (int i = 0; i < k; i++) {
                anterior = atual;
                atual = atual.proximo;
            }
            
            anterior.proximo = atual.proximo;
            tamanho--;
            return atual;
        }

        public Pedido buscarPorId(int idPedido) {
            Pedido atual = cabeca;
            while (atual != null) {
                if (atual.idPedido == idPedido) return atual;
                atual = atual.proximo;
            }
            return null;
        }


        public Pedido buscarPorPosicao(int posicao) {
            if (posicao >= tamanho || posicao < 0) return null;
            
            Pedido atual = cabeca;
            for (int i = 0; i < posicao; i++) {
                atual = atual.proximo;
            }
            return atual;
        }


        public void imprimirProximosN(int n) {
            Pedido atual = cabeca;
            int contador = 0;
            
            while (atual != null && contador < n) {
                System.out.printf("Pedido %d: Cliente=%s, Item=%s, Qtd=%d, Valor=%.2f, Tipo=%s%n",
                    atual.idPedido, atual.nomeCliente, atual.item, atual.quantidade, 
                    atual.valor, atual.tipo);
                atual = atual.proximo;
                contador++;
            }
        }

        public void imprimirLista() {
            if (estaVazia()) {
                System.out.println("Lista de pedidos vazia");
                return;
            }
            
            Pedido atual = cabeca;
            while (atual != null) {
                System.out.printf("Pedido %d: Cliente=%s, Item=%s, Qtd=%d, Valor=%.2f, Tipo=%s%n",
                    atual.idPedido, atual.nomeCliente, atual.item, atual.quantidade, 
                    atual.valor, atual.tipo);
                atual = atual.proximo;
            }
        }
    }

    public static void main(String[] args) {

        ListaPedidos lista = new ListaPedidos();
        

        Pedido p1 = new Pedido(1, "João", "X-Burger", 1, 20.0, TipoPedido.NORMAL);
        Pedido p2 = new Pedido(2, "Maria", "X-Salada", 2, 25.0, TipoPedido.URGENTE);
        Pedido p3 = new Pedido(3, "Pedro", "Hot Dog", 1, 15.0, TipoPedido.PRIORITARIO);
        

        lista.inserirPedido(p1);
        lista.inserirPedido(p2);
        lista.inserirPedido(p3);
        
        System.out.println("Lista de pedidos inicial:");
        lista.imprimirLista();
        
        System.out.println("\nPróximos 2 pedidos:");
        lista.imprimirProximosN(2);
        

        Pedido removido = lista.removerInicio();
        System.out.println("\nPedido atendido: " + removido.idPedido);
        
        System.out.println("\nLista após atendimento:");
        lista.imprimirLista();
    }
}
