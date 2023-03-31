import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

import { environment } from 'src/environments/environment'

import { Product } from '../common/product'
import { ProductCategory } from '../common/product-category'

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    private productUrl = `${environment.apiroot}/api/products`
    private categoryUrl = `${environment.apiroot}/api/product-category'`

    constructor(private httpClient: HttpClient) { }

    private getProducts(term: string): Observable<Product[]> {

        return this.httpClient.get<GetResponseProducts>(term).pipe(map(response => response._embedded.products))

    }

    getProduct(id: number): Observable<Product> {

        const productUrl = `${this.productUrl}/${id}`
        return this.httpClient.get<Product>(productUrl)

    }

    getProductListPaginate(page: number, size: number, id: number): Observable<GetResponseProducts> {

        const searchUrl = `${this.productUrl}/search/findByCategoryId?id=${id}&page=${page}&size=${size}`
        return this.httpClient.get<GetResponseProducts>(searchUrl)

    }


    getProductList(id: number): Observable<Product[]> {

        const searchUrl = `${this.productUrl}/search/findByCategoryId?id=${id}`
        return this.getProducts(searchUrl)

    }

    searchProducts(term: string): Observable<Product[]> {

        const searchUrl = `${this.productUrl}/search/findByNameContaining?name=${term}`
        return this.getProducts(searchUrl)

    }

    searchProductsPaginate(page: number, size: number, term: string): Observable<GetResponseProducts> {

        const searchUrl = `${this.productUrl}/search/findByNameContaining?name=${term}&page=${page}&size=${size}`
        return this.httpClient.get<GetResponseProducts>(searchUrl)

    }

    getProductCategories(): Observable<ProductCategory[]> {

        return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(
            map(response => response._embedded.productCategory)
        )

    }

}

interface GetResponseProducts {

    _embedded: {
        products: Product[]
    },
    page: {
        size: number,
        totalElements: number,
        totalPages: number,
        number: number
    }
}

interface GetResponseProductCategory {

    _embedded: {
        productCategory: ProductCategory[]
    }

}
