import { FileHandel} from "./FileHandel.model";

export interface Product {
  productId: number,
  productName: String,
  productDescription: String,
  productDiscountedPrice: number,
  productActualPrice: number,
  productImages: FileHandel[]

}
