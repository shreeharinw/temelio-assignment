import React, { useState } from 'react';
import { Dialog } from 'primereact/dialog';
import { TabView, TabPanel } from 'primereact/tabview';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import './AddData.css'; // Custom CSS

const AddData = ({ visible, onHide }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const [foundationData, setFoundationData] = useState({
    email: ''
  });
  const [nonProfitData, setNonProfitData] = useState({
    name: '',
    address: '',
    email: ''
  });
  const [errors, setErrors] = useState({
    foundationEmail: '',
    nonProfitName: '',
    nonProfitAddress: '',
    nonProfitEmail: ''
  });

  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const validateFields = () => {
    let valid = true;
    let newErrors = {
      foundationEmail: '',
      nonProfitName: '',
      nonProfitAddress: '',
      nonProfitEmail: ''
    };

    if (activeIndex === 0) {
      if (!foundationData.email) {
        newErrors.foundationEmail = 'Email is required.';
        valid = false;
      } else if (!validateEmail(foundationData.email)) {
        newErrors.foundationEmail = 'Please enter a valid email address.';
        valid = false;
      }
    } else if (activeIndex === 1) {
      if (!nonProfitData.name) {
        newErrors.nonProfitName = 'Name is required.';
        valid = false;
      }
      if (!nonProfitData.address) {
        newErrors.nonProfitAddress = 'Address is required.';
        valid = false;
      }
      if (!nonProfitData.email) {
        newErrors.nonProfitEmail = 'Email is required.';
        valid = false;
      } else if (!validateEmail(nonProfitData.email)) {
        newErrors.nonProfitEmail = 'Please enter a valid email address.';
        valid = false;
      }
    }

    setErrors(newErrors);
    return valid;
  };

  const handleSave = () => {
    if (!validateFields()) {
      return;
    }

    if (activeIndex === 0) {
      // Save Foundation data
      axios.post('http://localhost:8080/api/foundations', foundationData)
        .then(response => {
          console.log('Foundation saved successfully:', response.data);
          onHide();
        })
        .catch(error => {
          console.error('Error saving Foundation:', error);
        });
    } else if (activeIndex === 1) {
      // Save Non-Profit data
      axios.post('http://localhost:8080/api/nonprofits', nonProfitData)
        .then(response => {
          console.log('Non-Profit saved successfully:', response.data);
          onHide();
        })
        .catch(error => {
          console.error('Error saving Non-Profit:', error);
        });
    }
  };

  return (
    <Dialog visible={visible} onHide={onHide} header="Add Data" modal style={{ width: '30vw' }} position="center">
      <div className="dialog-container p-fluid">
        <TabView activeIndex={activeIndex} onTabChange={(e) => setActiveIndex(e.index)} className="tab-view">
          <TabPanel header="Add Foundation">
            <div className="p-grid p-fluid">
              <div className="p-col">
                <div className="p-field">
                  <label htmlFor="email">Email</label>
                  <InputText
                    id="email"
                    type="email"
                    value={foundationData.email}
                    onChange={(e) => setFoundationData({ ...foundationData, email: e.target.value })}
                  />
                  {errors.foundationEmail && <small className="p-error">{errors.foundationEmail}</small>}
                </div>
              </div>
            </div>
          </TabPanel>
          <TabPanel header="Add Non-Profit">
            <div className="p-grid p-fluid">
              <div className="p-col">
                <div className="p-field">
                  <label htmlFor="name">Name</label>
                  <InputText
                    id="name"
                    type="text"
                    value={nonProfitData.name}
                    onChange={(e) => setNonProfitData({ ...nonProfitData, name: e.target.value })}
                  />
                  {errors.nonProfitName && <small className="p-error">{errors.nonProfitName}</small>}
                </div>
                <div className="p-field">
                  <label htmlFor="address">Address</label>
                  <InputText
                    id="address"
                    type="text"
                    value={nonProfitData.address}
                    onChange={(e) => setNonProfitData({ ...nonProfitData, address: e.target.value })}
                  />
                  {errors.nonProfitAddress && <small className="p-error">{errors.nonProfitAddress}</small>}
                </div>
                <div className="p-field">
                  <label htmlFor="nonProfitEmail">Email</label>
                  <InputText
                    id="nonProfitEmail"
                    type="email"
                    value={nonProfitData.email}
                    onChange={(e) => setNonProfitData({ ...nonProfitData, email: e.target.value })}
                  />
                  {errors.nonProfitEmail && <small className="p-error">{errors.nonProfitEmail}</small>}
                </div>
              </div>
            </div>
          </TabPanel>
        </TabView>
        <div className="p-d-flex p-jc-end">
          <Button label="Cancel" className="p-button-text" onClick={onHide} />
          <Button label="Save" className="p-button-primary" onClick={handleSave} />
        </div>
      </div>
    </Dialog>
  );
};

export default AddData;
