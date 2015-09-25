//
//  ListViewController.swift
//  Scavenger Hunt
//
//  Created by Macbook Retina on 9/24/15.
//  Copyright © 2015 Beiwen Liu. All rights reserved.
//

import Foundation
import UIKit //Talk about table view and table view cells

class ListViewController: UITableViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate { //UITableViewController is the super class
    
    let myManager = ItemsManager()
    
//    var itemsList = [ScavengerHuntItem(name: "Cat"),
//        ScavengerHuntItem(name: "Mouse"),
//        ScavengerHuntItem(name: "🚝")]
    
    @IBAction func unwindToList (segue: UIStoryboardSegue) {
        if segue.identifier == "DoneItem" {
            let addVC = segue.sourceViewController as! AddViewController
            if let newItem = addVC.newItem {
                myManager.items += [newItem]
                myManager.save()
                let indexPath = NSIndexPath(forRow: myManager.items.count - 1, inSection: 0)
                tableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
            }
        }
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return myManager.items.count
    }
    
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell { //Tableview calls this everytime it needs a new cell. NS stands for next step. UI stands for User interface. IB interface builder. Prefix that lets you know which of the cocoa APIs you're using.
        
        let cell = tableView.dequeueReusableCellWithIdentifier("ListViewCell", forIndexPath: indexPath)
        
        let item = myManager.items[indexPath.row] //Extracting the item that I want
        
        if item.isComplete {
            cell.accessoryType = .Checkmark
            cell.imageView?.image = item.photo
        } else {
            cell.accessoryType = .None
            cell.imageView?.image = nil
        }
        
        cell.textLabel?.text = item.name //This will satisfy the compiler since it is name, and not ScavengerHunt
        
        return cell
    }
    
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let imagePicker = UIImagePickerController()
        
        if UIImagePickerController.isSourceTypeAvailable(.Camera) {
            imagePicker.sourceType = .Camera
        } else {
            imagePicker.sourceType = .PhotoLibrary
        }
        
        imagePicker.delegate = self
        presentViewController(imagePicker, animated: true, completion: nil)
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        if let indexPath = tableView.indexPathForSelectedRow {
            let selectedItem = myManager.items[indexPath.row]
            let photo = info[UIImagePickerControllerOriginalImage] as! UIImage
            selectedItem.photo = photo
            myManager.save()
            dismissViewControllerAnimated(true, completion: { () -> Void in
                self.tableView.reloadRowsAtIndexPaths([indexPath], withRowAnimation: .Automatic)
            })
        }
    }
    
    
    
}
