/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import nodos.Llave;
import nodos.NodoB;

/**
 *
 * @author user-ubunto
 */
public class BTree<Tipo> {

    private int T;
    
    public BTree(int t) {
        T = t;
        root = new NodoB<>(T);
        root.n = 0;
        root.leaf = true;
    }

    private NodoB<Tipo> root;

    // Search key
    private NodoB<Tipo> Search(NodoB<Tipo> x, int key) {
        int i = 0;
        if (x == null) {
            return x;
        }
        for (i = 0; i < x.n; i++) {
            if (key < x.key[i].llave) {
                break;
            }
            if (key == x.key[i].llave) {
                return x;
            }
        }
        if (x.leaf) {
            return null;
        } else {
            return Search(x.child[i], key);
        }
    }
    
    private Tipo buscar(NodoB<Tipo> x, int key){
        int i = 0;
        if (x == null) {
            return null;
        }
        for (i = 0; i < x.n; i++) {
            if (key < x.key[i].llave) {
                break;
            }
            if (key == x.key[i].llave) {
                return x.key[i].getValor();
            }
        }
        if (x.leaf) {
            return null;
        } else {
            return buscar(x.child[i], key);
        }
        //return null;
    }
    
    public Tipo buscar(String llaveStr){
        int llave = Integer.parseInt(llaveStr);
        return this.buscar(root, llave);
    }

    // Splitting the node
    private void Split(NodoB<Tipo> x, int pos, NodoB<Tipo> y) {
        NodoB<Tipo> z = new NodoB(T);
        z.leaf = y.leaf;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.key[j] = y.key[j + T];
        }
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.child[j] = y.child[j + T];
            }
        }
        y.n = T - 1;
        for (int j = x.n; j >= pos + 1; j--) {
            x.child[j + 1] = x.child[j];
        }
        x.child[pos + 1] = z;

        for (int j = x.n - 1; j >= pos; j--) {
            x.key[j + 1] = x.key[j];
        }
        x.key[pos] = y.key[T - 1];
        x.n = x.n + 1;
    }

    // Inserting a value
    public void Insert(final Llave key) {
        NodoB<Tipo> r = root;
        if (r.n == 2 * T - 1) {
            NodoB<Tipo> s = new NodoB(T);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            Split(s, 0, r);
            insertValue(s, key);
        } else {
            insertValue(r, key);
        }
    }

    // Insert the node
    final private void insertValue(NodoB<Tipo> x, Llave k) {

        if (x.leaf) {
            int i = 0;
            for (i = x.n - 1; i >= 0 && k.llave < x.key[i].llave; i--) {
                x.key[i + 1] = x.key[i];
            }
//            x.key[i + 1] = k.llave;
            x.key[i + 1] = k;
            x.n = x.n + 1;
        } else {
            int i = 0;
            for (i = x.n - 1; i >= 0 && k.llave < x.key[i].llave; i--) {
            }
            ;
            i++;
            NodoB<Tipo> tmp = x.child[i];
            if (tmp.n == 2 * T - 1) {
                Split(x, i, tmp);
                if (k.llave > x.key[i].llave) {
                    i++;
                }
            }
            insertValue(x.child[i], k);
        }

    }

    public void Show() {
        Show(root);
    }
        
    // Display
    private void Show(NodoB<Tipo> x) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            //System.out.print(x.key[i] + " ");
            //System.out.println("");
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                Show(x.child[i]);
            }
        }
    }

    // Check if present
    public boolean Contain(int k) {
        if (this.Search(root, k) != null) {
            return true;
        } else {
            return false;
        }
    }

    public NodoB<Tipo> getRoot() {
        return root;
    }
    
    
}
