import { Injectable } from '@angular/core'
import { CartItem } from '../common/cart-item'
import { BehaviorSubject, Subject } from 'rxjs'

@Injectable({
    providedIn: 'root'
})
export class CartService {

    cartItems: CartItem[] = []

    totalPrice: Subject<number> = new BehaviorSubject<number>(0)
    totalQuantity: Subject<number> = new BehaviorSubject<number>(0)

    constructor() { }

    addToCart(item: CartItem) {

        let alreadyExistsInCart: boolean = false
        let existingCartItem: CartItem = new CartItem()

        if (this.cartItems.length > 0) {
            existingCartItem = this.cartItems.find(tempCartItem => tempCartItem.id === item.id)!
            alreadyExistsInCart = (existingCartItem != undefined)
        }

        if (alreadyExistsInCart) {
            existingCartItem.quantity++
        }
        else {
            this.cartItems.push(item)
        }
        this.computeCartTotals()

    }

    computeCartTotals() {

        let totalPriceValue: number = 0
        let totalQuantityValue: number = 0

        for (let currentCartItem of this.cartItems) {
            totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice!
            totalQuantityValue += currentCartItem.quantity
        }
        
        this.totalPrice.next(totalPriceValue)
        this.totalQuantity.next(totalQuantityValue)

        //this.logCartData(totalPriceValue, totalQuantityValue)

    }

    logCartData(totalPriceValue: number, totalQuantityValue: number) {

        console.log('Contents of the cart')
        for (let tempCartItem of this.cartItems) {
            const subTotalPrice = tempCartItem.quantity * tempCartItem.unitPrice!
            console.log(`name: ${tempCartItem.name}, quantity=${tempCartItem.quantity}, unitPrice=${tempCartItem.unitPrice}, subTotalPrice=${subTotalPrice}`)
        }
        console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`)
        console.log('----')

    }

    decrementQuantity(item: CartItem) {

        item.quantity--
        if (item.quantity === 0) {
            this.remove(item)
        }
        else {
            this.computeCartTotals()
        }

    }

    remove(item: CartItem) {

        const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.id === item.id)
        if (itemIndex > -1) {
            this.cartItems.splice(itemIndex, 1)
            this.computeCartTotals()
        }

    }

}
