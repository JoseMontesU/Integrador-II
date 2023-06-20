export interface Producto{
    metadata: Metadatum[];
    product:  HorariosProduct;
}

export interface Metadatum {
    date: string;
    code: string;
    type: string;
}

export interface HorariosProduct {
    products: ProductElement[];
}

export interface ProductElement {
    id:       number;
    name:     string;
    price:    number;
    account:  number;
    category: Category;
    picture:  string;
}

export interface Category {
    id:          number;
    name:        string;
    description: string;
}
