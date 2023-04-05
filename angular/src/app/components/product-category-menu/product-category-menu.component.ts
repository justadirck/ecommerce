import { Component, OnInit } from '@angular/core';
import { ProductCategory } from 'src/app/common/product-category';
import { ProductService } from 'src/app/services/product.service';

@Component({
    selector: 'app-product-category-menu',
    templateUrl: './product-category-menu.component.html',
    styleUrls: ['./product-category-menu.component.css']
})
export class ProductCategoryMenuComponent implements OnInit {

    categories: ProductCategory[] = [];
    activeId: number = 1;

    constructor(private productService: ProductService) {

    }

    ngOnInit() {

        this.productService.getProductCategories()
            .subscribe( data => { this.categories = data} )

    }

}
