import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/common/product';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { CartService } from 'src/app/services/cart.service';

@Component({
    selector: 'app-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: Product[] = []
    searchMode: boolean = false
    currentCategoryId: number = 1
    previousCategoryId: number = 1

    page: number = 1
    pagesize: number = 25
    totalsize: number = 0

    previousKeyword: string = ""

    constructor(private productService: ProductService, private cartService: CartService, private route: ActivatedRoute) {

    }

    ngOnInit() {

        this.route.paramMap.subscribe(() => {
            this.listProducts()
        })

    }

    listProducts() {

        this.searchMode = this.route.snapshot.paramMap.has('keyword')

        if (this.searchMode) {
            this.handleSearchProducts()
        }
        else {
            this.handleListProducts()
        }

    }

    handleSearchProducts() {

        const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!

        if (this.previousKeyword != theKeyword) {
            this.page = 1
        }

        this.previousKeyword = theKeyword

        this.productService.searchProductsPaginate(this.page - 1, this.pagesize, theKeyword)
            .subscribe(this.processResult())

    }

    handleListProducts() {

        const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id')

        if (hasCategoryId) {
            this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!
        } else {
            this.currentCategoryId = 1
        }

        if (this.previousCategoryId != this.currentCategoryId) {
            this.page = 1
        }

        this.previousCategoryId = this.currentCategoryId

        this.productService.getProductListPaginate(this.page - 1, this.pagesize, this.currentCategoryId)
            .subscribe(this.processResult())
    }

    updatePageSize(pageSize: string) {
        this.pagesize = +pageSize;
        this.page = 1;
        this.listProducts()
    }

    processResult(): any {

        return (data: any) => {
            this.products = data._embedded.products
            this.page = data.page.number + 1
            this.pagesize = data.page.size
            this.totalsize = data.page.totalElements
        }

    }

    addToCart(product: Product) {

        let theCartItem = new CartItem(product.id, product.name, product.imageUrl, product.unitPrice)

        this.cartService.addToCart(theCartItem)

    }

}
